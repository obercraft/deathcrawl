package net.sachau.deathcrawl.gui.card;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableSet;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.conditions.Condition;
import net.sachau.deathcrawl.gui.images.TileSet;

public class ConditionBox extends HBox {

    public ConditionBox(Card card) {
        super();
        getStyleClass().add("card");
        setAlignment(Pos.BOTTOM_CENTER);

        HBox items = new HBox();

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
