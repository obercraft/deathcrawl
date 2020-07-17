package net.sachau.deathcrawl.cards.classes;

import net.sachau.deathcrawl.cards.Basic;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.CardEffect;
import net.sachau.deathcrawl.cards.Character;
import net.sachau.deathcrawl.cards.actions.Momentum;
import net.sachau.deathcrawl.cards.items.Knife;
import net.sachau.deathcrawl.effects.Armored;
import net.sachau.deathcrawl.effects.Stealthy;
import net.sachau.deathcrawl.keywords.Keyword;

@Character (uniqueId = "Thief", startingDeck = {Knife.class, Momentum.class, Momentum.class})
public class Thief extends Card {

	public Thief() {
		super("Thief", 7, 0);
		addKeywords(Keyword.BASIC, Keyword.CREATURE, Keyword.ROGUE);
		addEffect(CardEffect.Phase.PREPARE, new Stealthy());
	}


}

