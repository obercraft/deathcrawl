package net.sachau.deathcrawl.card.effect;

import net.sachau.deathcrawl.card.Card;
import net.sachau.deathcrawl.gui.images.Tile;

public class Mark extends CardEffect {
    public Mark() {
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

    @Override
    public void remove(Card card) {

    }
}
