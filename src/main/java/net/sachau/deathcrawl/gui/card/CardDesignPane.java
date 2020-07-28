package net.sachau.deathcrawl.gui.card;

import javafx.geometry.Pos;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import net.sachau.deathcrawl.Logger;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.gui.images.Tile;
import net.sachau.deathcrawl.gui.images.TileSet;

public class CardDesignPane extends VBox {

    public CardDesignPane(Card card) {
        super();
        setMinHeight(CardView.HEIGHT-50);
        setMaxHeight(CardView.HEIGHT-50);
        setAlignment(Pos.TOP_CENTER);
        this.getStyleClass().add("card");

        CardDesign cardDesign;

        //setAlignment(Pos.);

        HBox image = new HBox();

        image.setMinHeight(100);
        image.setMaxHeight(100);
        image.getChildren().add(TileSet.getInstance().getTile(Tile.DEATHCRAWL_SMALL));

        VBox nameband = new VBox();

        nameband.getStyleClass().add("card-nameband");
        Text name = new Text(card.toString());
        name.getStyleClass().add("card-nameband-text");
        Tooltip keywords = new Tooltip(card.getCardKeyWords());
        Tooltip.install(name, keywords);
        nameband.getChildren().add(name);

        TextFlow text = new TextFlow();
        text.getStyleClass().add("card-text");

        if (CardText.getText(card.getName()) != null) {
            text.getChildren().addAll(CardText.getText(card.getName()));
        }


        TextFlow flavor = new TextFlow();

        // getChildren().addAll(TileSet.getInstance().getTile(Tile.DEATHCRAWL), nameband, text);
        getChildren().addAll(image, nameband, text);



    }
}
