package net.sachau.deathcrawl.cards.classes;

import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.CardEffect;
import net.sachau.deathcrawl.effects.Armored;
import net.sachau.deathcrawl.effects.Guarded;
import net.sachau.deathcrawl.effects.Stealthy;
import net.sachau.deathcrawl.keywords.Keyword;

public class Warrior extends Card {

	public Warrior() {
		super("Warrior", 10, 0);
		addKeywords(Keyword.CREATURE, Keyword.FIGHTER);
		addEffect(CardEffect.Phase.PREPARE, new Armored());
	}


}

