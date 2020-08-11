package net.sachau.deathcrawl.gui.images;

public enum Tile {

    ROGUE(1, 39, 28),
    FIGHTER(1, 39, 28),
    CLERIC(1, 39, 28),
    PALADIN(1, 39, 28),
    WIZARD(1, 39, 28),

    DEATHCRAWL(0,0, 0, 100, 100),
    DEATHCRAWL_SMALL(0, 0, 0),
    ARMOR(0,1, 7),
    GUARD(0,2, 6),
    STEALTH(0,2,0),
    POISONOUS(0,1, 0),
    PARTY(1,34,29),
    PLAINS(1,21,9),

    MARKED(0,4,3);

    private int index;
    private int x, y, width, height;

    Tile(int index, int x, int y, int width, int height) {
        this.index = index;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    Tile(int index, int x, int y) {
        this.index = index;
        this.x = x;
        this.y = y;
        this.width = WIDTH;
        this.height = HEIGHT;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public static final int HEIGHT = 32;
    public static final int WIDTH = 32;

}
