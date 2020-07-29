package net.sachau.deathcrawl.effects;

import net.sachau.deathcrawl.cards.Card;

public class Prone extends CardEffect {

    @Override
    public void trigger(Card sourceCard, Card targetCard) {
            targetCard.getConditions().add(new Prone());
    }

    @Override
    public void remove(Card card) {

    }
}
