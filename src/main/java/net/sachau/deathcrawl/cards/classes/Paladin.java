package net.sachau.deathcrawl.cards.classes;

import net.sachau.deathcrawl.cards.Basic;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.CardEffect;
import net.sachau.deathcrawl.cards.Character;
import net.sachau.deathcrawl.effects.Armored;
import net.sachau.deathcrawl.keywords.Keyword;

@Character (uniqueId = "Paladin")
public class Paladin extends Card {

	public Paladin() {
		super("Paladin", 8, 0);
		setText("Warrior");
		addKeywords(Keyword.BASIC, Keyword.CREATURE, Keyword.PALADIN, Keyword.CASTER);
		addEffect(CardEffect.Phase.PREPARE, new Armored());
	}


}

