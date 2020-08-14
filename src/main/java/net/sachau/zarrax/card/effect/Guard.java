package net.sachau.zarrax.card.effect;

import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.gui.images.Tile;

public class Guard extends CardEffect {

    public Guard() {
        super();
        setTile(Tile.GUARD);
    }

    @Override
    public void trigger(Card sourceCard, Card targetCard) {
        targetCard.getConditions().add(new Guard());
    }

    @Override
    public void remove(Card card) {

    }
}
