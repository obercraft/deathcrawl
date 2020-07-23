package net.sachau.deathcrawl.gui.map;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.io.Serializable;
import java.util.Objects;

public class MapCoord implements Serializable {

  public enum Type {
    START("Valley", Color.BLUE),
    VALLEY("Valley", Color.LIGHTGREEN),
    CAVES("Caves", Color.LIGHTGRAY),
    MOUNTAINS ("Mountains", Color.DARKGRAY),
    WOODS ("Woods", Color.DARKGREEN);

    private String name;
    private Color color;

    Type(String name, Color paint) {
      this.name = name;
      this.color = paint;
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
  }

  private int column, row;
  private double x, y;
  private Type type;

  public MapCoord(int column, int row, double x, double y, Type type) {
    this.x = x;
    this.y = y;
    this.column = column;
    this.row = row;
    this.type = type;
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
