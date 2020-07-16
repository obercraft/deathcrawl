package net.sachau.deathcrawl.conditions;

import net.sachau.deathcrawl.gui.images.Tile;
import net.sachau.deathcrawl.gui.images.TileSet;

public abstract class Condition {
    /*
    ARMORED(1, Tile.ARMOR),
    GUARD(1, Tile.GUARD),
    STEALTH(1, Tile.STEALTH),
    POISONOUS(1, Tile.STEALTH);
    ;

     */

    private int amount;
    private Tile tile;

    public Condition(int amount, Tile tile) {

        this.amount = amount;
        this.tile = tile;
    }

    public int getAmount() {
        return amount;
    }

    public Tile getTile() {
        return tile;
    }
}
