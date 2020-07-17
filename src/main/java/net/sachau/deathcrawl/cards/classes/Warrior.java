package net.sachau.deathcrawl.cards.classes;

import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.CardEffect;
import net.sachau.deathcrawl.cards.Character;
import net.sachau.deathcrawl.cards.actions.Charge;
import net.sachau.deathcrawl.cards.actions.Momentum;
import net.sachau.deathcrawl.cards.items.Knife;
import net.sachau.deathcrawl.effects.Armored;
import net.sachau.deathcrawl.keywords.Keyword;
import net.sachau.deathcrawl.momentum.MomentumAction;

@Character (uniqueId = "Warrior", startingDeck = {Knife.class, Momentum.class, Momentum.class})
public class Warrior extends Card {

	public Warrior() {
		super("Warrior", 10, 0);
		addKeywords(Keyword.BASIC, Keyword.CREATURE, Keyword.FIGHTER);
		addEffect(CardEffect.Phase.PREPARE, new Armored());

		addMomentum(new MomentumAction(new Charge(), 3));

	}



}

