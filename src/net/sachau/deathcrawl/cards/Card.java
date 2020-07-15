package net.sachau.deathcrawl.cards;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import net.sachau.deathcrawl.GameState;
import net.sachau.deathcrawl.dto.Creature;
import net.sachau.deathcrawl.effects.Effect;

import java.io.Serializable;
import java.util.*;

public abstract class Card implements Serializable {

	private long id;

	private String name;

	private Map<Effect.Phase, List<Effect>> effects;

	private String command;

	private String text;


	private SimpleIntegerProperty hits = new SimpleIntegerProperty(1);

	private SimpleBooleanProperty visible = new SimpleBooleanProperty();

	private Creature owner;

	private Deck deck;

	public Card(String name) {
		super();
		this.name = name;
		this.id = GameState.createId();
		this.effects = new HashMap<>();

	}

	public Card(String name, Creature owner) {
		super();
		this.name = name;
		this.id = GameState.createId();
		this.effects = new HashMap<>();
		this.owner = owner;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		return visible.get();
	}

	public SimpleBooleanProperty visibleProperty() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible.set(visible);
	}

	public Creature getOwner() {
		return owner;
	}

	public void setOwner(Creature owner) {
		this.owner = owner;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Card card = (Card) o;
		return id == card.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "Card{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}

	public int getHits() {
		return hits.get();
	}

	public SimpleIntegerProperty hitsProperty() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits.set(hits);
	}

	public void attack(Card target, int attack) {

		if (owner != null) {
			attack += owner.getAttackBonus();
		}

		int hits = target.getHits();
		if (attack > 0) {
			hits -= attack;
		}

		target.setHits(hits);
		if (hits <= 0) {
			target.getDeck().remove(target);
		}

	}

	public Deck getDeck() {
		return deck;
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}
}
