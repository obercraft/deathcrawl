package net.sachau.deathcrawl.card.effect;

import net.sachau.deathcrawl.card.Card;

public class Prone extends CardEffect {

    @Override
    public void trigger(Card sourceCard, Card targetCard) {
            targetCard.getConditions().add(new Prone());
    }

    @Override
    public void remove(Card card) {

    }
}
