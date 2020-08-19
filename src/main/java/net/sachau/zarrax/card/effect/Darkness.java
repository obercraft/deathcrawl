package net.sachau.zarrax.card.effect;

import net.sachau.zarrax.card.Card;

public class Darkness extends CardEffect {
    public Darkness() {
        super();
    }

    @Override
    public void trigger(Card sourceCard, Card targetCard) {
        targetCard.getConditions().add(new Darkness());
    }

    @Override
    public void remove(Card card) {

    }
}
