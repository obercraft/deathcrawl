package net.sachau.deathcrawl.gui.card;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import net.sachau.deathcrawl.card.Creature;
import net.sachau.deathcrawl.card.type.Environment;
import net.sachau.deathcrawl.engine.GameEvent;
import net.sachau.deathcrawl.card.Card;
import net.sachau.deathcrawl.card.keyword.Keyword;
import net.sachau.deathcrawl.gui.images.Tile;
import net.sachau.deathcrawl.gui.images.TileSet;

import java.util.Observer;

public abstract class CardView extends AnchorPane implements Observer {

    // see also top of application.css
    public static final double HEIGHT = 320;
    public static final double HEIGHT_SMALL = 320;

    public static final double WIDTH = 240;
    private Card card;

    public CardView(Card card, String css) {
        super();
        this.card = card;

        GameEvent.getInstance()
                .addObserver(this);

        Image image = new Image(this.getClass()
                .getResourceAsStream("/card3.png"));

        this.setHeight(HEIGHT);
        this.setWidth(WIDTH);
        this.getChildren()
                .add(new ImageView(image));


        if (card.getKeywords()
                .contains(Keyword.CREATURE)) {

            CornerValueBox damageBox = new CornerValueBox(card.damageProperty(), null);
            getChildren()
                    .add(damageBox);
            damageBox.relocate(25,275);

            CornerValueBox healthBox = new CornerValueBox(card.hitsProperty(), card.maxHitsProperty());
            getChildren()
                    .add(healthBox);
            healthBox.relocate(200,275);


        }

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
        if (borderTile != null) {
            ImageView border = TileSet.getInstance()
                    .getTile(borderTile);
            border.setX(185);
            border.setY(25);
            getChildren().add(border);
        }

        Text cardName = new Text(card.getName());
        cardName.getStyleClass().add("card-title-text");
        cardName.setX(35);
        cardName.setY(32);
        getChildren().add(cardName);

    }

}

