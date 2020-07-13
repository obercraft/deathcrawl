package net.sachau.deathcrawl.cards;

import net.sachau.deathcrawl.effects.Damage;
import net.sachau.deathcrawl.effects.Effect.Phase;

public class Goblin extends Card implements Hazard {

	public Goblin() {
		super("goblin");
		setText("A goblin");
		this.setHits(3);
		addEffect(Phase.HAZARD, new Damage(1));
	}



}
