package net.sachau.zarrax.gui;

import javafx.collections.ListChangeListener;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.engine.ApplicationContext;
import net.sachau.zarrax.engine.GameEngine;
import net.sachau.zarrax.engine.Player;
import net.sachau.zarrax.gui.card.CardTile;

public class PartyPane extends ScrollPane {


    VBox content = new VBox();


    public PartyPane() {
        super();
        setMaxHeight(CardTile.HEIGHT);
        setMaxHeight(CardTile.WIDTH);
        setHbarPolicy(ScrollBarPolicy.NEVER);
        setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        setContent(content);

        for (Card card : ApplicationContext.getPlayer().getParty()) {
            content.getChildren().add(new PartyPaneRow(card));
        }


        ApplicationContext.getPlayer().getParty().addListener(new ListChangeListener<Card>() {
            @Override
            public void onChanged(Change<? extends Card> change) {

                change.next();
            }
        });


    }

}
