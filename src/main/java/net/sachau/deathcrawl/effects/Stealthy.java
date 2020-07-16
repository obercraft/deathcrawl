package net.sachau.deathcrawl.effects;

import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.CardEffect;
import net.sachau.deathcrawl.conditions.Condition;
import net.sachau.deathcrawl.conditions.Stealth;

public class Stealthy extends CardEffect {
    @Override
    public void trigger(Card targetCard) {
        targetCard.getConditions().add(new Stealth());
    }
}
