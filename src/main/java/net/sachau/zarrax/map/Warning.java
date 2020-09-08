package net.sachau.zarrax.map;

import net.sachau.zarrax.gui.images.Tile;

public enum Warning {

    BONES,
    DANK,
    FLUTTER,
    HOWL,
    ROAR,
    SLITHER,
    SMOKER,
    STINK,
    ;
    private Tile tile;

    Warning() {
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }
}
