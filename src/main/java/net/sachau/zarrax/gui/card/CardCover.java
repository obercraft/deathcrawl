package net.sachau.zarrax.gui.card;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import net.sachau.zarrax.gui.images.Tile;
import net.sachau.zarrax.gui.images.TileSet;

public class CardCover extends StackPane {

  public CardCover(String cssClass) {
    super();

    getStyleClass().add(cssClass);
    getStyleClass().add("card-cover");


    ImageView imageView = TileSet.getInstance()
      .getTile(Tile.DEATHCRAWL);

    this.getChildren()
      .addAll(imageView);
  }
}
