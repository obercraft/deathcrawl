package net.sachau.deathcrawl.cards;

import net.sachau.deathcrawl.GameState;
import net.sachau.deathcrawl.effects.Effect;

import java.io.Serializable;
import java.util.*;

public abstract class Card implements Serializable {

	private long id;

	private String name;

	private Map<Effect.Phase, List<Effect>> effects;

	private String command;

	private String text;

	private int hits;

	private boolean visible;


	public Card(String name) {
		super();
		this.name = name;
		this.id = GameState.getInstance().createId();
		effects = new HashMap<>();

	}

	private Map<Effect.Phase, List<Effect>> getEffects() {
		return effects;
	}

	private List<Effect> getPhaseEffects(Effect.Phase phase) {
		return effects.get(phase) != null ? effects.get(phase) : new LinkedList<>();
	}

	public void triggerPhaseEffects(Effect.Phase phase) {
		List<Effect> phaseEffects = getPhaseEffects(phase);
		if (phaseEffects != null) {
			for (Effect e : phaseEffects) {
				e.trigger(this);
			}
		}
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

	public void damage(int amount) {
		if (amount > 0) {
			int hits = this.getHits();
			hits -= amount;
			this.setHits(hits);
		}

	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Card card = (Card) o;
		return id == card.id &&
				hits == card.hits &&
				visible == card.visible &&
				Objects.equals(name, card.name) &&
				Objects.equals(effects, card.effects) &&
				Objects.equals(command, card.command) &&
				Objects.equals(text, card.text);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, effects, command, text, hits, visible);
	}

	public boolean isPlayable() {
		Deck cards = GameState.getInstance()
				.getPlayer()
				.getHand();
		for (Card card : cards.getAll()) {
			if (card.getId() == getId()) {
				return true;
			}
		}
		return false;
	}

	public boolean isDrawable() {
		Deck cards = GameState.getInstance()
				.getPlayer()
				.getDraw();
		for (Card card : cards.getAll()) {
			if (card.getId() == getId()) {
				return true;
			}
		}
		return false;
	}

	public boolean isDiscarded() {
		Deck cards = GameState.getInstance()
				.getPlayer()
				.getDiscard();
		for (Card card : cards.getAll()) {
			if (card.getId() == getId()) {
				return true;
			}
		}
		return false;
	}
}
