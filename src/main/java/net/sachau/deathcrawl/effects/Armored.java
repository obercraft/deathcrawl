package net.sachau.deathcrawl.effects;

import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.CardEffect;
import net.sachau.deathcrawl.conditions.Armor;

public class Armored extends CardEffect {
    @Override
    public void trigger(Card targetCard) {
        targetCard.getConditions().add(new Armor());
    }
}
