package net.sachau.deathcrawl.cards.classes;

import net.sachau.deathcrawl.cards.CardEffect;
import net.sachau.deathcrawl.cards.CharacterCard;
import net.sachau.deathcrawl.cards.Deck;
import net.sachau.deathcrawl.effects.Armored;
import net.sachau.deathcrawl.keywords.Keyword;
import net.sachau.deathcrawl.momentum.MomentumActions;

@StartingCharacter
public class Paladin extends CharacterCard {

	public Paladin() {
		super("Paladin", 8, 0, "Paladin", Deck.builder(), MomentumActions.builder());
		addKeywords(Keyword.CREATURE, Keyword.PALADIN, Keyword.CASTER);
		addEffect(CardEffect.Phase.PREPARE, new Armored());
	}


}

