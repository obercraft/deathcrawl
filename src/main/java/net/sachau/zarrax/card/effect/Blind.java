package net.sachau.zarrax.card.effect;

import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.gui.images.Tile;

public class Blind extends CardEffect {
    public Blind() {
        super();
    }

    @Override
    public void trigger(Card sourceCard, Card targetCard) {
        targetCard.getConditions().add(new Blind());
    }

    @Override
    public void remove(Card card) {

    }
}
