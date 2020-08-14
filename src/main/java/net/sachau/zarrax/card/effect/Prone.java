package net.sachau.zarrax.card.effect;

import net.sachau.zarrax.card.Card;

public class Prone extends CardEffect {

    @Override
    public void trigger(Card sourceCard, Card targetCard) {
            targetCard.getConditions().add(new Prone());
    }

    @Override
    public void remove(Card card) {

    }
}
