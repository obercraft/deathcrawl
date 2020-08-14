package net.sachau.zarrax.gui.card;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableSet;
import javafx.scene.text.TextFlow;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.effect.CardEffect;

public class ConditionFlow extends TextFlow {

    public ConditionFlow(Card card) {

        super();
        for (CardEffect condition : card.getConditions()) {
            if (condition.getTile() != null) {
                getChildren()
                        .add(new ConditionItem(condition));
            }
        }

        card.conditionsProperty().addListener(new ChangeListener<ObservableSet<CardEffect>>() {
            @Override
            public void changed(ObservableValue<? extends ObservableSet<CardEffect>> observable, ObservableSet<CardEffect> oldValue, ObservableSet<CardEffect> newValue) {

                if (oldValue != null && oldValue.size() > 0) {
                    if (getChildren().size() > 0) {
                        getChildren()
                                .remove(0, getChildren().size());
                    }
                }

                if (newValue != null && newValue.size() > 0) {
                    for (CardEffect c : newValue) {
                        if (c.getTile() != null) {
                            getChildren()
                                    .add(new ConditionItem(c));
                        }
                    }
                }
            }
        });
    }
}
