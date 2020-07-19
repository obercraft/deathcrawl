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

    getStyleClass().add("card");
    getStyleClass().add("card-cover");


    ImageView imageView = TileSet.getInstance()
      .getTile(Tile.DEATHCRAWL);

    this.getChildren()
      .addAll(imageView);
  }
}
