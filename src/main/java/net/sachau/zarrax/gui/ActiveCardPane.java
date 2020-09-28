package net.sachau.zarrax.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.HBox;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.engine.ApplicationContext;
import net.sachau.zarrax.engine.GameEngine;
import net.sachau.zarrax.gui.card.CardTile;

public class ActiveCardPane extends HBox {

    CardTile current;

    public ActiveCardPane() {
        super();

        setMinHeight(CardTile.HEIGHT);
        setMinHeight(CardTile.WIDTH);
        setHeight(CardTile.HEIGHT);
        setWidth(CardTile.WIDTH);

        Card card = ApplicationContext.getGameEngine()
                .getCurrentCard();

        if (card != null) {
            current = new CardTile(card, "");
            getChildren().add(current);
        }

        ApplicationContext.getGameEngine()
                .currentInitiativeProperty()
                .addListener(new ChangeListener<Number>() {
                                 @Override
                                 public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                                     if (current != null) {
                                         getChildren().remove(current);
                                         current = null;
                                     }
                                     current = new CardTile(ApplicationContext.getGameEngine()
                                             .getCurrentCard(), "");
                                     getChildren().add(current);
                                 }
                             }
                );


    }

}
