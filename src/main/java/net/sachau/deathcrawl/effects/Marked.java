package net.sachau.deathcrawl.effects;

import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.gui.images.Tile;

public class Marked extends CardEffect {
    public Marked() {
        super();
        setTile(Tile.MARKED);
    }

    @Override
    public void trigger(Card sourceCard, Card targetCard) {
        int amount = 1;
        if (sourceCard != null) {
            amount = Math.max(1, sourceCard.getDamage());
        }


    }
}
