package net.sachau.deathcrawl.gui.card;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class CornerValueBox extends StackPane {

    SimpleIntegerProperty property;
    SimpleIntegerProperty maxProperty;
    
    public CornerValueBox(SimpleIntegerProperty simpleIntegerProperty, SimpleIntegerProperty maxProperty, String position, String cssClass) {

        super();
        this.property = simpleIntegerProperty;
        this.maxProperty = maxProperty; 
        getStyleClass().add(cssClass);
        getStyleClass().add("value-" + position);
    
        HBox textBox = new HBox();
        textBox.getStyleClass()
                .add("value-box-" + position);
        Text hits = new Text(getHitString());
        hits.getStyleClass()
                .add("card-corner-text");
        //hits.setFont(CardCache.get("card", 12));
        textBox.getChildren()
                .add(hits);
        getChildren().add(textBox);

        this.property
                .addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        hits.setText(getHitString());
                    }
                });

        this.property
                .addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        hits.setText(getHitString());
                    }
                });

        if (this.maxProperty != null) {
            this.maxProperty
                    .addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                            hits.setText(getHitString());
                        }
                    });

            this.maxProperty
                    .addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                            hits.setText(getHitString());
                        }
                    });
            
        }

        
    }

    private String getHitString() {
        if (this.maxProperty != null) {
            return "" + this.property.get() + "/" + this.maxProperty.get();
        } else {
            return "" + this.property.get();
        }
    }
}
