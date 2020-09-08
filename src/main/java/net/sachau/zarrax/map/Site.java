package net.sachau.zarrax.map;

import net.sachau.zarrax.gui.images.Tile;

public enum Site {

    ALTAR,
    HOARD,
    LAIR,
    LOST_CASTLE,
    LOST_CITY,
    ;
    private Tile tile;
    private TerrainList possibleTerrains;


    Site() {
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public TerrainList getPossibleTerrains() {
        return possibleTerrains;
    }

    public void setPossibleTerrains(TerrainList possibleTerrains) {
        this.possibleTerrains = possibleTerrains;
    }
}
