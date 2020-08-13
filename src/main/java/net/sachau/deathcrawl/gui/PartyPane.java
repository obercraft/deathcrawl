package net.sachau.deathcrawl.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.sachau.deathcrawl.card.Card;
import net.sachau.deathcrawl.engine.GameEngine;
import net.sachau.deathcrawl.engine.Player;
import net.sachau.deathcrawl.gui.card.CardTile;

public class PartyPane extends ScrollPane {


    VBox content = new VBox();


    public PartyPane(Player player) {
        super();
        setMaxHeight(CardTile.HEIGHT);
        setMaxHeight(CardTile.WIDTH);
        setHbarPolicy(ScrollBarPolicy.NEVER);
        setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        setContent(content);

        for (Card card : player.getParty()) {
            content.getChildren().add(new PartyPaneRow(card));
        }


        player.getParty().addListener(new ListChangeListener<Card>() {
            @Override
            public void onChanged(Change<? extends Card> change) {

                change.next();
            }
        });


    }

}
