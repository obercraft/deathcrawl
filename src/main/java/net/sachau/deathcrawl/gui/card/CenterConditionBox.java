package net.sachau.deathcrawl.gui.card;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableSet;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.effects.CardEffect;

public class CenterConditionBox extends StackPane {

    public CenterConditionBox(Card card, String cssClass) {

        super();
        getStyleClass().add(cssClass);


        HBox items = new HBox();
        items.getStyleClass().add("value-bottom-center");
        for (CardEffect condition : card.getConditions()) {
            if (condition.getTile() != null) {
                items.getChildren()
                        .add(new ConditionItem(condition));
            }
        }

        getChildren().add(items);

        card.conditionsProperty().addListener(new ChangeListener<ObservableSet<CardEffect>>() {
            @Override
            public void changed(ObservableValue<? extends ObservableSet<CardEffect>> observable, ObservableSet<CardEffect> oldValue, ObservableSet<CardEffect> newValue) {

                if (oldValue != null && oldValue.size() > 0) {
                    if (getChildren().size() > 0) {
                        items.getChildren()
                                .remove(0, getChildren().size());
                    }
                }

                if (newValue != null && newValue.size() > 0) {
                    for (CardEffect c : newValue) {
                        if (c.getTile() != null) {
                            items.getChildren()
                                    .add(new ConditionItem(c));
                        }
                    }
                }
            }
        });
    }
}
