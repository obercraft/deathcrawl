package net.sachau.deathcrawl.gui.images;

public enum Tile {

    DEATHCRAWL(0, 0, 100, 100),
    DEATHCRAWL_SMALL(0, 0),
    ARMOR(1, 7),
    GUARD(2, 6),
    STEALTH(2,0),
    POISONOUS(1, 0),
    PARTY(3,4),

    MARKED(4,3);

    private int x, y, width, height;

    Tile(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    Tile(int x, int y) {
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

    public static final int HEIGHT = 32;
    public static final int WIDTH = 32;

}
