package net.sachau.deathcrawl.card.effect;

import net.sachau.deathcrawl.card.Card;
import net.sachau.deathcrawl.gui.images.Tile;

public class Stealth extends CardEffect {

    public Stealth() {
        super();
        setTile(Tile.STEALTH);
    }

    @Override
    public void trigger(Card sourceCard, Card targetCard) {
        targetCard.getConditions().add(new Stealth());
    }

    @Override
    public void remove(Card card) {

    }
}
