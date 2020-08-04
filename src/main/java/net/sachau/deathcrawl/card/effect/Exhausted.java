package net.sachau.deathcrawl.card.effect;

import net.sachau.deathcrawl.card.Card;
import net.sachau.deathcrawl.gui.images.Tile;

public class Exhausted extends CardEffect {

    public Exhausted() {
        super();
        this.setTile(Tile.ARMOR);
    }

    @Override
    public void trigger(Card sourceCard, Card targetCard) {
            targetCard.getConditions().add(new Exhausted());
    }

    @Override
    public void remove(Card card) {

    }
}
