package net.sachau.deathcrawl.cards;

import net.sachau.deathcrawl.effects.Damage;
import net.sachau.deathcrawl.effects.Effect.Phase;


public class Knife extends Card {

	public Knife() {
		super("knife");
		setText("A knife");
		addEffect(Phase.PLAY, new Damage(1));
		// TODO Auto-generated constructor stub
	}

}
