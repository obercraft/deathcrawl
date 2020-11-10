package net.sachau.zarrax.gui;

import javafx.collections.ListChangeListener;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.gui.card.CardTile;
import net.sachau.zarrax.v2.GEngine;
import net.sachau.zarrax.v2.GState;

public class PartyPane extends ScrollPane {

    GState state = GEngine.getBean(GState.class);

    VBox content = new VBox();


    public PartyPane() {
        super();
        setMaxHeight(CardTile.HEIGHT);
        setMaxHeight(CardTile.WIDTH);
        setHbarPolicy(ScrollBarPolicy.NEVER);
        setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        setContent(content);

        for (Card card : state.getPlayer().getParty()) {
            content.getChildren().add(new PartyPaneRow(card));
        }


        state.getPlayer().getParty().addListener(new ListChangeListener<Card>() {
            @Override
            public void onChanged(Change<? extends Card> change) {

                change.next();
            }
        });


    }

}
