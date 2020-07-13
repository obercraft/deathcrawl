package net.sachau.deathcrawl.effects;


public abstract class Effect {

	public enum Phase {
		PREPARE,
		DRAW,
		DISCARD,
		DESTROY,
		PLAY,
		HAZARD,
	}
	
	private String text;
	
	int amount = 1;
	
	
	
	public Effect() {
		super();
	}

	abstract public void trigger(Target target);

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
	
	
	
	
}
