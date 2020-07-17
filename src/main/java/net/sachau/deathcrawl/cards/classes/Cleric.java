package net.sachau.deathcrawl.cards.classes;

import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.Character;
import net.sachau.deathcrawl.cards.items.Knife;
import net.sachau.deathcrawl.cards.spells.HealingTouch;
import net.sachau.deathcrawl.keywords.Keyword;

@Character (uniqueId = "Cleric", startingDeck = {Knife.class, Knife.class, HealingTouch.class})
public class Cleric extends Card {

	public Cleric() {
		super("Cleric", 7, 0);
		addKeywords(Keyword.BASIC, Keyword.CREATURE, Keyword.CASTER, Keyword.CLERIC);

	}


}

