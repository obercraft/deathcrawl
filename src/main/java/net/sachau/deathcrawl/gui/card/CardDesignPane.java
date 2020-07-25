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
        try {
            cardDesign = CardDesignParser.parseFor(card);
        } catch (Exception e) {
            cardDesign = new CardDesign(card.getClass().getSimpleName());
            if (Logger.isDebugEnabled()) {
                cardDesign.getCardText().add(new Text(e.getClass().getSimpleName() + " " + e.getMessage()));

            }

        }


        //setAlignment(Pos.);

        HBox image = new HBox();

        image.setMinHeight(100);
        image.setMaxHeight(100);
        image.getChildren().add(TileSet.getInstance().getTile(Tile.DEATHCRAWL_SMALL));

        VBox nameband = new VBox();

        nameband.getStyleClass().add("card-nameband");
        Text name = new Text(cardDesign.getName());
        name.getStyleClass().add("card-nameband-text");
        Tooltip keywords = new Tooltip(card.getCardKeyWords());
        Tooltip.install(name, keywords);
        nameband.getChildren().add(name);

        TextFlow text = new TextFlow();
        text.getStyleClass().add("card-text");
        if (cardDesign.getCardText() != null) {
            text.getChildren().addAll(cardDesign.getCardText());
        }

        TextFlow flavor = new TextFlow();

        // getChildren().addAll(TileSet.getInstance().getTile(Tile.DEATHCRAWL), nameband, text);
        getChildren().addAll(image, nameband, text);



    }
}
