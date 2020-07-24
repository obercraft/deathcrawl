package net.sachau.deathcrawl.cards.characters;

import net.sachau.deathcrawl.cards.CharacterCard;
import net.sachau.deathcrawl.cards.Deck;
import net.sachau.deathcrawl.cards.items.Knife;
import net.sachau.deathcrawl.cards.spells.HealingTouch;
import net.sachau.deathcrawl.keywords.Keyword;
import net.sachau.deathcrawl.momentum.MomentumActions;

@StartingCharacter
public class Cleric extends CharacterCard {

    public Cleric() {
        super("Cleric", 7, 0, "Cleric",
				Deck.builder().add(Knife.class, Knife.class, HealingTouch.class), MomentumActions.builder());
        addKeywords(Keyword.CREATURE, Keyword.CASTER, Keyword.CLERIC);

    }


}

