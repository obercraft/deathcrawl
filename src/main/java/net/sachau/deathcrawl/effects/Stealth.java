package net.sachau.deathcrawl.effects;

import net.sachau.deathcrawl.cards.Card;
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
