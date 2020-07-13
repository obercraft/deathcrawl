package net.sachau.deathcrawl.effects;

public class Damage extends Effect {

	public Damage(int amount) {
		
	}

	@Override
	public void trigger(Target target) {
		target.damage(amount);		
	}

}
