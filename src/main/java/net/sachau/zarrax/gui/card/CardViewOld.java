package net.sachau.zarrax.gui.card;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.engine.GameEvent;
import net.sachau.zarrax.gui.images.Tile;
import net.sachau.zarrax.gui.images.TileSet;

import java.util.Observer;

public abstract class CardViewOld extends StackPane implements Observer {

    // see also top of application.css
    public static final double HEIGHT = 88 * 3;
    public static final double HEIGHT_SMALL = 88 * 3 - 100;

    public static final double WIDTH = 63 * 3;
    private Card card;

    public CardViewOld(Card card, String cssClass) {
        super();
        this.card = card;

        GameEvent.getInstance()
                .addObserver(this);
        this.getStyleClass()
                .add(cssClass);
        this.getStyleClass()
                .add("card-background");


        CardDesignPane cardDesignPane = new CardDesignPane(card, cssClass);


        this.getChildren()
                .add(cardDesignPane);

//        if (card.getKeywords()
//                .contains(Keyword.CREATURE)) {
//            CornerValueBox leftBox = new CornerValueBox(card.damageProperty(), null, "bottom-left", cssClass);
//            CornerValueBox rightBox = new CornerValueBox(card.hitsProperty(), card.maxHitsProperty(), "bottom-right", cssClass);
//            this.getChildren()
//                    .addAll(leftBox, rightBox);
//        } else if (card instanceof Environment) {
//            CornerValueBox rightBox = new CornerValueBox(((Environment) card).threatProperty(), null, "bottom-right", cssClass);
//            this.getChildren()
//                    .addAll(rightBox);
//
//        }

        Tile borderTile = card.getKeywords().getCharacterTile();
        if (borderTile != null) {
            ImageView image = TileSet.getInstance()
                    .getTile(borderTile);
            getChildren().add(image);
            //image.relocate(0,0);
        }

    }

}

