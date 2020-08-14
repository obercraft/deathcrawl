package net.sachau.zarrax.card.effect;

import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.gui.images.Tile;

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
