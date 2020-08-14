package net.sachau.zarrax.gui.card;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.gui.images.Tile;
import net.sachau.zarrax.gui.images.TileSet;


public class CardDesignPane extends VBox {

    private Card card;
    private VBox nameband;
    public CardDesignPane(Card card, String cssClass) {
        super();
        this.card = card;

        setMinHeight(CardView.HEIGHT-50);
        setMaxHeight(CardView.HEIGHT-50);
        setAlignment(Pos.TOP_CENTER);
        this.getStyleClass().add(cssClass);

        HBox image = new HBox();

        image.setMinHeight(100);
        image.setMaxHeight(100);
        image.getChildren().add(TileSet.getInstance().getTile(Tile.DEATHCRAWL_SMALL));

        nameband = new VBox();

        nameband.getStyleClass().addAll("card-nameband", "inactive");
        Text name = new Text(card.getName() + "@" +  card.getId());
        name.getStyleClass().add("card-nameband-text");
        nameband.getChildren().add(name);

        TextFlow text = new TextFlow();
        text.getStyleClass().add("card-text");


        text.getChildren().add(new ConditionFlow(card));

        if (CardText.getText(card.getName()) != null) {
            text.getChildren().addAll(CardText.getText(card.getName()));
        }


        TextFlow flavor = new TextFlow();

        // getChildren().addAll(TileSet.getInstance().getTile(Tile.DEATHCRAWL), nameband, text);
        if (cssClass.contains("small")) {
            getChildren().addAll(nameband, text);
        } else {
            getChildren().addAll(image, nameband, text);
        }

        card.activeProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (Boolean.TRUE.equals(newValue)) {
                        nameband.getStyleClass().remove("inactive");
                        nameband.getStyleClass().add("active");
                    } else {
                        nameband.getStyleClass().remove("active");
                        nameband.getStyleClass().add("inactive");

                    }
            }
        });


    }


    public Card getCard() {
        return card;
    }


}
