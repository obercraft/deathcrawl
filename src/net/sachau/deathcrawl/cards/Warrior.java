package net.sachau.deathcrawl.cards;

import net.sachau.deathcrawl.effects.Move;
import net.sachau.deathcrawl.effects.Effect.Phase;

public class Warrior extends Card {

	public Warrior() {
		super("warrior");
		setHits(10);
		setText("A warrior");
		addEffect(Phase.PREPARE, new Move(1));
	}


}
