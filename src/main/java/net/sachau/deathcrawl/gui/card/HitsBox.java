package net.sachau.deathcrawl.gui.card;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.CardCache;

public class HitsBox extends StackPane {

    Card card;


    public HitsBox(Card card) {

      super();
      getStyleClass().add("card");
      getStyleClass().add("hits-layer");
        this.card = card;

        HBox textBox = new HBox();
      textBox.getStyleClass().add("card-corner");
        Text hits = new Text(getHitString());
        hits.getStyleClass().add("card-corner-text");
        //hits.setFont(CardCache.get("card", 12));
        textBox.getChildren().add(hits);
        getChildren().add(textBox);

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
        return "ABC";
        // return "" + card.getHits() + "/" + card.getMaxHits();
    }
}
