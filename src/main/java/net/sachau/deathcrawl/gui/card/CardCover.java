package net.sachau.deathcrawl.gui.card;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import net.sachau.deathcrawl.gui.images.Tile;
import net.sachau.deathcrawl.gui.images.TileSet;

public class CardCover extends StackPane {

    public CardCover() {
        super();

        Rectangle rectangle = new Rectangle(CardTile.WIDTH, CardTile.HEIGHT);
        rectangle.setFill(Color.ANTIQUEWHITE);
        rectangle.setStyle("-fx-fill: red; -fx-stroke: black; -fx-stroke-width: 5;");

        ImageView imageView = TileSet.getInstance()
                .getTile(Tile.DEATHCRAWL);

        this.getChildren()
                .addAll(rectangle, imageView);
    }
}
