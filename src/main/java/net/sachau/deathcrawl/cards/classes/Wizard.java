package net.sachau.deathcrawl.cards.classes;

import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.Character;
import net.sachau.deathcrawl.cards.actions.Momentum;
import net.sachau.deathcrawl.cards.items.Knife;
import net.sachau.deathcrawl.effects.Armored;
import net.sachau.deathcrawl.keywords.Keyword;

@Character (uniqueId = "Wizard", startingDeck = {Knife.class, Momentum.class, Momentum.class})
public class Wizard extends Card {

	public Wizard() {
		super("Wizard", 6, 0);
		addKeywords(Keyword.BASIC, Keyword.CREATURE, Keyword.CASTER, Keyword.WIZARD);
	}
}

