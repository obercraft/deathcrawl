package net.sachau.deathcrawl.effects;

import net.sachau.deathcrawl.cards.Card;

public class Exhausted extends CardEffect {

    @Override
    public void trigger(Card sourceCard, Card targetCard) {
            targetCard.getConditions().add(new Exhausted());
    }

    @Override
    public void remove(Card card) {

    }
}
