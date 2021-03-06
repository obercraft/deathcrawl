package net.sachau.zarrax.card.keyword;

import net.sachau.zarrax.gui.image.Tile;

public enum Keyword {

    CASTER(),

    FIGHTER( Tile.FIGHTER),
    ROGUE(Tile.ROGUE),
    WIZARD(Tile.WIZARD),
    CLERIC(Tile.CLERIC),
    PALADIN(Tile.PALADIN),

    SIMPLE, // simple cards can be played by all classes
    ITEM (true),
    WEAPON (true),
    POTION (true),
    MONSTER(Tile.MONSTER), CREATURE, ACTION,
    RANGED(true), PERMANENT(),
    RETALIATE(true),
    FLYING(true),
    VICIOUS(true),
    SMALL(true),

    BLIND(true), DARKNESS(true),


    EXHAUSTED(true), GUARDED(true), PRONE(true), ARMOR(true),
    STEALTH(true),
    LONG_EXHAUSTED(true);

    private boolean onCard;

    private Tile borderTile;

    Keyword(boolean onCard) {
        this.onCard = onCard;
    }


    Keyword(boolean onCard, Tile borderTile) {
        this.onCard = onCard;
        this.borderTile = borderTile;
    }

    Keyword(Tile borderTile) {
        this.borderTile = borderTile;
    }


    Keyword() {
    }

    public boolean isOnCard() {
        return onCard;
    }

    public void setOnCard(boolean onCard) {
        this.onCard = onCard;
    }

    public Tile getBorderTile() {
        return borderTile;
    }

    public void setBorderTile(Tile borderTile) {
        this.borderTile = borderTile;
    }
}
