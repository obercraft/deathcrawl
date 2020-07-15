package net.sachau.deathcrawl.gui.card;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import net.sachau.deathcrawl.cards.Card;

public class HitsBox extends HBox {

    public HitsBox(Card card) {

        setAlignment(Pos.BOTTOM_RIGHT);
        Text hits = new Text("" + card.getHits());

        getChildren().add(hits);

        card.hitsProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                hits.setText("" +  card.getHits());
            }
        });

    }
}
