package net.sachau.zarrax.gui;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.sachau.zarrax.engine.GameEventContainer;
import net.sachau.zarrax.engine.GameEvent;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.catalog.Catalog;
import net.sachau.zarrax.card.type.Character;
import net.sachau.zarrax.card.Deck;
import net.sachau.zarrax.engine.Player;
import net.sachau.zarrax.gui.card.CardSelect;
import net.sachau.zarrax.gui.card.CardTile;
import net.sachau.zarrax.util.DiceUtils;

import java.util.*;

public class PartySelection extends VBox implements Observer {

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
                    .get(Character.class);
            for (Card b : basic) {
                Character card = (Character) b;
                card.setVisible(true);
                if (card.getLevelCards() != null) {
                    for (Card c : card.getLevelCards()) {
                        c.setOwner(player);
                    }
                }
                available.getChildren().add(new CardSelect(this, card, player.partyProperty(), cssClass));
                availableCharacters.add(new Character(card));
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
                player.getParty().clear();
                DiceUtils.createRandomParty(player);
                GameEvent.getInstance().send(GameEventContainer.Type.PARTYDONE);
                GameEvent.getInstance().send(GameEventContainer.Type.START_TURN);
                return;
            default:
                return;
        }
    }
}
