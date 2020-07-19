package net.sachau.deathcrawl.gui.map;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.Objects;

public class MapCoord {

  public enum Type {
    START("Valley", Color.ANTIQUEWHITE),
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

  private double x, y;
  private Type type;

  public MapCoord(double x, double y, Type type) {
    this.x = x;
    this.y = y;
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
}
