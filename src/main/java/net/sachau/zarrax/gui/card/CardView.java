package net.sachau.zarrax.gui.card;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextFlow;
import net.sachau.zarrax.engine.GameEvent;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.keyword.Keyword;
import net.sachau.zarrax.gui.text.CardText;
import net.sachau.zarrax.gui.Symbol;
import net.sachau.zarrax.gui.images.Tile;
import net.sachau.zarrax.gui.images.TileSet;
import org.apache.commons.lang3.StringUtils;

import java.util.Observer;

public abstract class CardView extends AnchorPane implements Observer {

    // see also top of application.css
    public static final double HEIGHT = 287; // 320;
    public static final double HEIGHT_SMALL = 287; //320;

    public static final double WIDTH = 240;
    private Card card;

    public CardView(Card card, String css) {
        super();
        this.card = card;

        GameEvent.getInstance()
                .addObserver(this);

        Image image = new Image(this.getClass()
                .getResourceAsStream("/images/card5.png"));

        this.setHeight(HEIGHT);
        this.setWidth(WIDTH);
        this.getChildren()
                .add(new ImageView(image));

        HBox rightBox;


        if (card.getKeywords()
                .contains(Keyword.CREATURE)) {

            rightBox = new CornerValueBox(card.hitsProperty(), card.maxHitsProperty());
        } else {
            rightBox = new HBox();
        }


        rightBox.setMaxHeight(32);
        rightBox.setMaxWidth(32);
        rightBox.setMinWidth(32);
        rightBox.setMinHeight(32);
        rightBox.setAlignment(Pos.CENTER);
        rightBox.getStyleClass()
                .add("border");
        getChildren().add(rightBox);
        rightBox.relocate(190, 270 - 32);


//            CornerValueBox leftBox = new CornerValueBox(card.damageProperty(), null, "bottom-left", cssClass");
//            CornerValueBox rightBox = new CornerValueBox(card.hitsProperty(), card.maxHitsProperty(), "bottom-right", cssClass);
//            this.getChildren()
//                    .addAll(leftBox, rightBox);
//        } else if (card instanceof Environment) {
//            CornerValueBox rightBox = new CornerValueBox(((Environment) card).threatProperty(), null, "bottom-right", cssClass);
//            this.getChildren()
//                    .addAll(rightBox);

//        }


        // TODO msachau bordertile
        Tile borderTile = null;
//                card.getKeywords()
//                .getCharacterTile();
        if (borderTile == null) {
            borderTile = Tile.ALL_CLASSES;
        }

        ImageView border = TileSet.getInstance()
                .getTile(borderTile);
        border.setX(184);
        border.setY(23);
        getChildren().add(border);


        CardText builder = CardText.builder();
        if (!StringUtils.isEmpty(card.getUniqueId())) {
            builder.symbol(Symbol.FA_DOT_CIRCLE.getText()).add(" ");
        }
        builder.add(card.getName());

        TextFlow cardName = builder.write();
        getChildren().add(cardName);
        cardName.relocate(30, 20);

    }

}

