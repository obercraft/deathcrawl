package net.sachau.deathcrawl.cards.characters;

import net.sachau.deathcrawl.Event;
import net.sachau.deathcrawl.cards.CharacterCard;
import net.sachau.deathcrawl.cards.Deck;
import net.sachau.deathcrawl.cards.actions.Momentum;
import net.sachau.deathcrawl.cards.items.Knife;
import net.sachau.deathcrawl.cards.items.ThrowingKnife;
import net.sachau.deathcrawl.effects.Stealthy;
import net.sachau.deathcrawl.keywords.Keyword;
import net.sachau.deathcrawl.momentum.MomentumActions;

@StartingCharacter
public class Thief extends CharacterCard {

	public Thief() {
		super("Thief", 7, 0, "Thief", Deck.builder().add(Knife.class, ThrowingKnife.class, Momentum.class), MomentumActions.builder());
		addKeywords(Keyword.CREATURE, Keyword.ROGUE);
		addEffect(Event.STARTTURN, new Stealthy());
	}


}

