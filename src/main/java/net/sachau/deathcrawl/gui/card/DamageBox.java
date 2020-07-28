package net.sachau.deathcrawl.gui.card;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import net.sachau.deathcrawl.cards.Card;

public class DamageBox extends HBox {

    Card card;


    public DamageBox(Card card) {

        this.card = card;

        setAlignment(Pos.BOTTOM_LEFT);
        Text hits = new Text(getDamageString());

        getChildren().add(hits);

        card.damageProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                hits.setText(getDamageString());
            }
        });
        
    }

    private String getDamageString() {
        return "" + card.getDamage();
    }
}
