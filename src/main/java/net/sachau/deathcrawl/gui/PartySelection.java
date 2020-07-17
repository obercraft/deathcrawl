package net.sachau.deathcrawl.gui;

import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import net.sachau.deathcrawl.Logger;
import net.sachau.deathcrawl.cards.Basic;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.Character;
import net.sachau.deathcrawl.cards.Deck;
import net.sachau.deathcrawl.dto.Player;
import net.sachau.deathcrawl.gui.card.CardSelect;
import net.sachau.deathcrawl.gui.card.CardTile;
import net.sachau.deathcrawl.gui.card.CardTileCache;
import net.sachau.deathcrawl.keywords.Keyword;
import org.reflections.Reflections;

import java.util.HashSet;
import java.util.Set;

public class PartySelection extends VBox {

    private Player player;

    private Set<String> uniqueIds = new HashSet<>();

    public PartySelection(Player player, Button partyDone, Button partyClear, int length) {
        super();
        this.player = player;


        this.setMinHeight(2 * CardTile.HEIGHT);

        ScrollPane availableScroll = new ScrollPane();
        availableScroll.setMinHeight(CardTile.HEIGHT +50);
        availableScroll.setMinWidth(CardTile.WIDTH);
        HBox available = new HBox();
        availableScroll.setContent(available);

        Reflections reflections = new Reflections("net.sachau.deathcrawl.cards");

        PartyArea partyArea = new PartyArea(player, length);

        Set<Class<?>> basic = reflections.getTypesAnnotatedWith(Character.class);

        for (Class b : basic) {
            Card card = null;
            try {
                card = (Card) b.newInstance();
                if (card.getKeywords().contains(Keyword.BASIC)) {
                    card.setVisible(true);
                    available.getChildren().add(new CardSelect(this, card, player.getParty(), partyDone));
                }
                Logger.log("Test");
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }




        getChildren().addAll(available, partyArea);


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
}
