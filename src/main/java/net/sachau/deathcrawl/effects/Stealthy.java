package net.sachau.deathcrawl.effects;

import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.gui.images.Tile;

public class Stealthy extends CardEffect {

    public Stealthy() {
        super();
        setTile(Tile.STEALTH);
    }

    @Override
    public void trigger(Card sourceCard, Card targetCard) {
        targetCard.getConditions().add(new Stealthy());
    }
}
