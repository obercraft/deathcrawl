package net.sachau.deathcrawl.cards;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sachau.deathcrawl.effects.Effect;
import net.sachau.deathcrawl.effects.Target;

public abstract class Card implements Target {

	private String name;

	private Map<Effect.Phase, List<Effect>> effects;

	private String text;

	private int hits;


	public Card(String name) {
		super();
		this.name = name;
		effects = new HashMap<>();

	}

	public Map<Effect.Phase, List<Effect>> getEffects() {
		return effects;
	}

	public List<Effect> getPhaseEffects(Effect.Phase phase) {
		return effects.get(phase) != null ? effects.get(phase) : new LinkedList<>();
	}


	public void addEffect(Effect.Phase phase, Effect effect) {
		if (effects.get(phase) == null) {
			effects.put(phase, new LinkedList<>());
		}
		effects.get(phase).add(effect);

	}

	public void setEffects(Map<Effect.Phase, List<Effect>> effects) {
		this.effects = effects;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getHits() {
		return hits;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	@Override
	public void damage(int amount) {
		if (amount > 0) {
			int hits = this.getHits();
			hits -= amount;
			this.setHits(hits);
		}

	}

}
