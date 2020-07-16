package net.sachau.deathcrawl.gui.card;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import net.sachau.deathcrawl.cards.Card;

public class HitsBox extends HBox {

    Card card;


    public HitsBox(Card card) {

        this.card = card;

        setAlignment(Pos.BOTTOM_RIGHT);
        Text hits = new Text(getHitString());

        getChildren().add(hits);

        card.hitsProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                hits.setText(getHitString());
            }
        });

        card.maxHitsProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                hits.setText(getHitString());
            }
        });


    }

    private String getHitString() {
        return "" + card.getHits() + "/" + card.getMaxHits();
    }
}
