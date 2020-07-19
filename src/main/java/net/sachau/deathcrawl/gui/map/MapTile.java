package net.sachau.deathcrawl.gui.map;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class MapTile extends Polygon {


  private MapCoord mapCoord;


  public MapTile(MapCoord mapCoord) {
    // creates the polygon using the corner coordinates

    this.mapCoord = mapCoord;

    double x = mapCoord.getX();
    double y = mapCoord.getY();

    getPoints().addAll(
      x, y,
      x, y + HexMap.r,
      x + HexMap.n, y + HexMap.r * 1.5,
      x + HexMap.TILE_WIDTH, y + HexMap.r,
      x + HexMap.TILE_WIDTH, y,
      x + HexMap.n, y - HexMap.r * 0.5
    );

    // set up the visuals and a click listener for the tile
    setFill(MapCoord.Type.VALLEY.getColor());
    setStrokeWidth(1);
    setStroke(Color.BLACK);
  }

  public MapCoord getMapCoord() {
    return mapCoord;
  }

  public void setMapCoord(MapCoord mapCoord) {
    this.mapCoord = mapCoord;
  }
}
