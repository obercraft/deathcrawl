package net.sachau.deathcrawl.effects;

import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.gui.images.Tile;

public class Guarded extends CardEffect {

    public Guarded() {
        super();
        setTile(Tile.GUARD);
    }

    @Override
    public void trigger(Card sourceCard, Card targetCard) {
        targetCard.getConditions().add(new Guarded());
    }

    @Override
    public void remove(Card card) {

    }
}
