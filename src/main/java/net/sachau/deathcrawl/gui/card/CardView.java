package net.sachau.deathcrawl.gui.card;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import net.sachau.deathcrawl.engine.GameEvent;
import net.sachau.deathcrawl.card.Card;
import net.sachau.deathcrawl.card.keyword.Keyword;
import net.sachau.deathcrawl.gui.CardText;
import net.sachau.deathcrawl.gui.Symbol;
import net.sachau.deathcrawl.gui.images.Tile;
import net.sachau.deathcrawl.gui.images.TileSet;
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
                .getResourceAsStream("/card5.png"));

        this.setHeight(HEIGHT);
        this.setWidth(WIDTH);
        this.getChildren()
                .add(new ImageView(image));

        HBox leftBox, rightBox;


        if (card.getKeywords()
                .contains(Keyword.CREATURE)) {

            leftBox = new CornerValueBox(card.damageProperty(), null);

            rightBox = new CornerValueBox(card.hitsProperty(), card.maxHitsProperty());
        } else {
            leftBox = new HBox();
            rightBox = new HBox();
        }

        leftBox.setMaxHeight(32);
        leftBox.setMaxWidth(32);
        leftBox.setMinWidth(32);
        leftBox.setMinHeight(32);
        leftBox.setAlignment(Pos.CENTER);
        leftBox.getStyleClass()
                .add("border");
        getChildren().add(leftBox);
        leftBox.relocate(17, 270 - 32);

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


        Tile borderTile = card.getKeywords()
                .getCharacterTile();
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
            builder.symbol(Symbol.FA_DOT_CIRCLE);
        }
        builder.add(card.getName());

        TextFlow cardName = builder.write();
        getChildren().add(cardName);
        cardName.relocate(35, 16);

    }

}

