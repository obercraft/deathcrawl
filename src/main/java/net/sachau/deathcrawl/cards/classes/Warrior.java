package net.sachau.deathcrawl.cards.classes;

import net.sachau.deathcrawl.cards.CardEffect;
import net.sachau.deathcrawl.cards.CharacterCard;
import net.sachau.deathcrawl.cards.Deck;
import net.sachau.deathcrawl.cards.actions.Charge;
import net.sachau.deathcrawl.cards.actions.Momentum;
import net.sachau.deathcrawl.cards.items.Knife;
import net.sachau.deathcrawl.conditions.Guard;
import net.sachau.deathcrawl.effects.Armored;
import net.sachau.deathcrawl.keywords.Keyword;
import net.sachau.deathcrawl.momentum.MomentumAction;
import net.sachau.deathcrawl.momentum.MomentumActions;

@StartingCharacter
public class Warrior extends CharacterCard {

    public Warrior() {
        super("Warrior", 10, 0, "Warrior", Deck.builder(false)
                        .add(new Knife(), new Momentum(), new Momentum()),
                MomentumActions.builder()
                        .addAction(new MomentumAction(new Charge(), 3)));
        addKeywords(Keyword.CREATURE, Keyword.FIGHTER);
        getConditions().add(new Guard());
    }


}

