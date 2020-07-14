package net.sachau.deathcrawl.effects;

import net.sachau.deathcrawl.cards.Card;

public class Damage extends Effect {

	public Damage(int amount) {
		
	}

	@Override
	public void trigger(Card target) {
		target.damage(amount);
	}

}
