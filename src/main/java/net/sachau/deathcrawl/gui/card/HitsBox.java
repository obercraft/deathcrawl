package net.sachau.deathcrawl.gui.card;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import net.sachau.deathcrawl.card.Card;

public class HitsBox extends StackPane {

    Card card;


    public HitsBox(Card card, String cssClass) {

      super();
      getStyleClass().add(cssClass);
      getStyleClass().add("hits-layer");
        this.card = card;

        HBox textBox = new HBox();
      textBox.getStyleClass().add("card-corner");
        Text hits = new Text(getHitString());
        hits.getStyleClass().add("card-corner-text");
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
        return "" + card.getHits() + "/" + card.getMaxHits();
    }
}
