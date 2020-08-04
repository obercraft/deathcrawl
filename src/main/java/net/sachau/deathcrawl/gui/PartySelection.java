package net.sachau.deathcrawl.gui;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.sachau.deathcrawl.engine.GameEventContainer;
import net.sachau.deathcrawl.engine.GameEvent;
import net.sachau.deathcrawl.card.Card;
import net.sachau.deathcrawl.card.catalog.Catalog;
import net.sachau.deathcrawl.card.type.StartingCharacter;
import net.sachau.deathcrawl.card.Deck;
import net.sachau.deathcrawl.engine.Player;
import net.sachau.deathcrawl.gui.card.CardSelect;
import net.sachau.deathcrawl.gui.card.CardTile;

import java.util.*;

public class PartySelection extends VBox implements Observer {


    public final static int PARTY_SIZE = 4;

    private Player player;

    private Set<String> uniqueIds = new HashSet<>();

    Deck availableCharacters = new Deck();

    public PartySelection(Player player, int length, String cssClass) {
        super();
        GameEvent.getInstance().addObserver(this);
        this.player = player;


        this.setMinHeight(2 * CardTile.HEIGHT);

        ScrollPane availableScroll = new ScrollPane();
        availableScroll.setMinHeight(CardTile.HEIGHT + 50);
        availableScroll.setMinWidth(CardTile.WIDTH);
        HBox available = new HBox();
        availableScroll.setContent(available);

         
        
        DeckPane partyArea = new DeckPane(player.partyProperty(), length, "card-small");
        try {
            List<Card> basic = Catalog.getInstance()
                    .get(StartingCharacter.class);
            for (Card b : basic) {
                StartingCharacter card = (StartingCharacter) b;
                card.setVisible(true);
                available.getChildren().add(new CardSelect(this, card, player.partyProperty(), cssClass));
                availableCharacters.add(new StartingCharacter(card));
            }
            getChildren().addAll(available, partyArea);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public Set<String> getUniqueIds() {
        return uniqueIds;
    }

    public void setUniqueIds(Set<String> uniqueIds) {
        this.uniqueIds = uniqueIds;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void update(Observable o, Object arg) {
        switch (GameEvent.getType(arg)) {
            case RANDOMPARTY:
                for (int i = 0; i < PARTY_SIZE; i++) {
                    availableCharacters.drawRandom(player.getParty());
                }

                GameEvent.getInstance().send(GameEventContainer.Type.PARTYDONE);
                GameEvent.getInstance().send(GameEventContainer.Type.STARTTURN);
                return;
            default:
                return;
        }
    }
}
