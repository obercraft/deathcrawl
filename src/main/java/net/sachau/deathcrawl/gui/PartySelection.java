package net.sachau.deathcrawl.gui;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.sachau.deathcrawl.Event;
import net.sachau.deathcrawl.Game;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.CardFactory;
import net.sachau.deathcrawl.cards.types.Character;
import net.sachau.deathcrawl.cards.Deck;
import net.sachau.deathcrawl.dto.Player;
import net.sachau.deathcrawl.gui.card.CardSelect;
import net.sachau.deathcrawl.gui.card.CardTile;
import org.reflections.Reflections;

import java.util.*;

public class PartySelection extends VBox implements Observer {


    public final static int PARTY_SIZE = 4;

    private Player player;

    private Set<String> uniqueIds = new HashSet<>();

    Deck availableCharacters = new Deck();

    public PartySelection(Player player, int length) {
        super();
        Game.events().addObserver(this);
        this.player = player;


        this.setMinHeight(2 * CardTile.HEIGHT);

        ScrollPane availableScroll = new ScrollPane();
        availableScroll.setMinHeight(CardTile.HEIGHT + 50);
        availableScroll.setMinWidth(CardTile.WIDTH);
        HBox available = new HBox();
        availableScroll.setContent(available);

        Reflections reflections = new Reflections("net.sachau.deathcrawl.cards");

        DeckPane partyArea = new DeckPane(player.getParty(), length);

        try {
            List<Card> basic = CardFactory.createCards("/cards/characters/starting");
            for (Card b : basic) {
                Character card = (Character) b;
                card.setVisible(true);
                available.getChildren().add(new CardSelect(this, card, player.getParty()));
                availableCharacters.add(card);
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
        switch (Game.get(arg)) {
            case RANDOMPARTY:
                for (int i = 0; i < PARTY_SIZE; i++) {
                    availableCharacters.drawRandom(player.getParty());
                }

                Game.events().send(Event.PARTYDONE);
                Game.events().send(Event.STARTTURN);
                return;
            default:
                return;
        }
    }
}
