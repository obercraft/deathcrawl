package net.sachau.zarrax.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.HBox;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.engine.GuiComponent;
import net.sachau.zarrax.gui.card.CardTile;
import net.sachau.zarrax.v2.GEngine;
import net.sachau.zarrax.v2.GState;

public class ActiveCardPane extends HBox {

    private GState state = GEngine.getBean(GState.class);

    CardTile current;

    public ActiveCardPane() {
        super();

        setMinHeight(CardTile.HEIGHT);
        setMinHeight(CardTile.WIDTH);
        setHeight(CardTile.HEIGHT);
        setWidth(CardTile.WIDTH);

        Card card = state.getCurrentCard();

        if (card != null) {
            current = new CardTile(card, "");
            getChildren().add(current);
        }

        state.currentInitiativeProperty()
                .addListener(new ChangeListener<Number>() {
                                 @Override
                                 public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                                     if (current != null) {
                                         getChildren().remove(current);
                                         current = null;
                                     }
                                     current = new CardTile(state.getCurrentCard(), "");
                                     getChildren().add(current);
                                 }
                             }
                );


    }

}
