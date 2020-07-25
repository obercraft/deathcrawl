package net.sachau.deathcrawl.gui.card;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableSet;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.conditions.Condition;

public class CenterConditionBox extends StackPane {

    public CenterConditionBox(Card card) {

        super();
        getStyleClass().add("card");


        HBox items = new HBox();
        items.getStyleClass().add("value-bottom-center");
        for (Condition condition : card.getConditions()) {
            items.getChildren().add(new ConditionItem(condition));
        }

        getChildren().add(items);

        card.conditionsProperty().addListener(new ChangeListener<ObservableSet<Condition>>() {
            @Override
            public void changed(ObservableValue<? extends ObservableSet<Condition>> observable, ObservableSet<Condition> oldValue, ObservableSet<Condition> newValue) {
                items.getChildren().remove(0, getChildren().size());

                if (newValue != null || newValue.size() > 0) {
                    for (Condition c : newValue) {
                        items.getChildren().add(new ConditionItem(c));
                    }
                }


            }
        });
    }
}
