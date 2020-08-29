package net.sachau.zarrax.gui.map;

import javafx.scene.paint.Color;
import net.sachau.zarrax.gui.images.Tile;
import net.sachau.zarrax.map.Land;
import net.sachau.zarrax.map.Site;

import java.io.Serializable;
import java.util.Objects;

public class MapCoord implements Serializable {

  public enum Type {
    WATER("Water", Color.BLUE),
    VALLEY("Valley", Color.LIGHTGREEN, Tile.PLAINS),
    HILL("Hills", Color.LIGHTGRAY),
    MOUNTAINS ("Mountains", Color.DARKGRAY),
    WOODS ("Woods", Color.DARKGREEN);

    private String name;
    private Color color;
    private Tile tile;

    Type(String name, Color paint) {
      this.name = name;
      this.color = paint;
    }

    Type(String name, Color paint, Tile tile) {
      this.name = name;
      this.color = paint;
      this.tile = tile;
    }


    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public Color getColor() {
      return color;
    }

    public void setColor(Color color) {
      this.color = color;
    }

    public Tile getTile() {
      return tile;
    }

    public void setTile(Tile tile) {
      this.tile = tile;
    }
  }

  private int column, row;
  private double x, y;
  private Type type;
  private Land land;
  private Site site;

  public MapCoord(int column, int row, double x, double y, Land land) {
    this.x = x;
    this.y = y;
    this.column = column;
    this.row = row;
    this.type = Type.valueOf(land.getClass().getSimpleName().toUpperCase());
    this.land = land;
  }

  public double getX() {
    return x;
  }

  public void setX(double x) {
    this.x = x;
  }

  public double getY() {
    return y;
  }

  public void setY(double y) {
    this.y = y;
  }

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public int getColumn() {
    return column;
  }

  public void setColumn(int column) {
    this.column = column;
  }

  public int getRow() {
    return row;
  }

  public void setRow(int row) {
    this.row = row;
  }

  public Land getLand() {
    return land;
  }

  public void setLand(Land land) {
    this.land = land;
  }

  public Site getSite() {
    return site;
  }

  public void setSite(Site site) {
    this.site = site;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MapCoord mapCoord = (MapCoord) o;
    return Double.compare(mapCoord.x, x) == 0 &&
      Double.compare(mapCoord.y, y) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  @Override
  public String toString() {
    return "MapCoord{" +
            "column=" + column +
            ", row=" + row +
            ", x=" + x +
            ", y=" + y +
            ", type=" + type +
            '}';
  }
}
