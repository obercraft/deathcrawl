package net.sachau.deathcrawl.effects;

import net.sachau.deathcrawl.cards.Card;
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
