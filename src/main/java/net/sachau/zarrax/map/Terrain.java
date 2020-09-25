package net.sachau.zarrax.map;

import net.sachau.zarrax.gui.image.Tile;

public enum Terrain {


    WATER,
    DESERT,
    CAVES,
    VALLEY,
    WOODS,
    HILL,
    MOUNTAIN,
    ;
    private float left, right;
    private int moveCost;
    private String tile;

    Terrain() {
        this.moveCost = 1;
    }

    Terrain(int moveCost, float left, float right) {
        this.left = left;
        this.right = right;
        this.moveCost = moveCost;
    }

    public float getLeft() {
        return left;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public float getRight() {
        return right;
    }

    public void setRight(float right) {
        this.right = right;
    }

    public int getMoveCost() {
        return moveCost;
    }

    public void setMoveCost(int moveCost) {
        this.moveCost = moveCost;
    }

    public String getTile() {
        return tile;
    }

    public void setTile(String tile) {
        this.tile = tile;
    }
}
