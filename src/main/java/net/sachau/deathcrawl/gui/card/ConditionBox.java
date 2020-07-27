package net.sachau.deathcrawl.gui.card;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableSet;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.effects.CardEffect;

public class ConditionBox extends HBox {

    public ConditionBox(Card card) {
        super();
        getStyleClass().add("card");
        setAlignment(Pos.BOTTOM_CENTER);

        HBox items = new HBox();

        for (CardEffect condition : card.getConditions()) {
            items.getChildren().add(new ConditionItem(condition));
        }

        getChildren().add(items);

        card.conditionsProperty().addListener(new ChangeListener<ObservableSet<CardEffect>>() {
            @Override
            public void changed(ObservableValue<? extends ObservableSet<CardEffect>> observable, ObservableSet<CardEffect> oldValue, ObservableSet<CardEffect> newValue) {
                items.getChildren().remove(0, getChildren().size());

                if (newValue != null || newValue.size() > 0) {
                    for (CardEffect c : newValue) {
                        items.getChildren().add(new ConditionItem(c));
                    }
                }


            }
        });

    }
}
