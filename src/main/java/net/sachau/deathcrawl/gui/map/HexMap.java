package net.sachau.deathcrawl.gui.map;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import net.sachau.deathcrawl.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class HexMap extends AnchorPane {

  public final static double r = 20; // the inner radius from hexagon center to outer corner
  public final static double n = Math.sqrt(r * r * 0.75); // the inner radius from hexagon center to middle of the axis
  public final static double TILE_HEIGHT = 2 * r;
  public final static double TILE_WIDTH = 2 * n;

  private AnchorPane map = new AnchorPane();

  List<MapTile> tilesSet = new ArrayList<>();

  public HexMap(double width, double height) {

    setHeight(height);
    setWidth(width);
    setMaxHeight(height);
    setMaxWidth(width);
    setMinWidth(width);
    setMinHeight(height);

    int rowCount = 10; // how many rows of tiles should be created
    int tilesPerRow = 10; // the amount of tiles that are contained in each row
    int xStartOffset = 40; // offsets the entire field to the right
    int yStartOffset = 40; // offsets the entire fiels downwards

    for (int x = 0; x < tilesPerRow; x++) {
      for (int y = 0; y < rowCount; y++) {
        double xCoord = x * TILE_WIDTH + (y % 2) * n + xStartOffset;
        double yCoord = y * TILE_HEIGHT * 0.75 + yStartOffset;

        Logger.log(xCoord + ", " + yCoord);
        MapTile tile = new MapTile(new MapCoord(xCoord, yCoord, MapCoord.Type.VALLEY));
        getChildren().add(tile);
        tilesSet.add(tile);
      }
    }

    fill(tilesSet, MapCoord.Type.START, 1);
    fill(tilesSet, MapCoord.Type.MOUNTAINS, 5);
    fill(tilesSet, MapCoord.Type.CAVES, 5);
    fill(tilesSet, MapCoord.Type.WOODS, 9);




    //Rectangle r = new Rectangle(0,0, 100,100);
    //map.getChildren().add(r);
    // getChildren().add(map);


  }

  private void fill(List<MapTile> tiles, MapCoord.Type type, int count) {
    int i = 0;
    while (i < count) {
      int randomNum = ThreadLocalRandom.current().nextInt(0, tiles.size());
      if (tiles.get(randomNum).getMapCoord().getType().equals(MapCoord.Type.VALLEY)) {
        tiles.get(randomNum).getMapCoord().setType(type);
        tiles.get(randomNum).setFill(type.getColor());
        i++;
      }
    }
  }

}
