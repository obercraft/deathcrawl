package net.sachau.deathcrawl.cards.characters;

import net.sachau.deathcrawl.Event;
import net.sachau.deathcrawl.cards.CharacterCard;
import net.sachau.deathcrawl.cards.Deck;
import net.sachau.deathcrawl.cards.items.Horse;
import net.sachau.deathcrawl.cards.items.Knife;
import net.sachau.deathcrawl.effects.Armored;
import net.sachau.deathcrawl.keywords.Keyword;
import net.sachau.deathcrawl.momentum.MomentumActions;

@StartingCharacter
public class Knight extends CharacterCard {

	public Knight() {
		super("Knight", 8, 0, "Paladin", Deck.builder().add(Horse.class, Knife.class, Knife.class), MomentumActions.builder());
		addKeywords(Keyword.CREATURE, Keyword.FIGHTER);
		addEffect(Event.STARTTURN, new Armored());
	}


}

