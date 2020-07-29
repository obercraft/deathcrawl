package net.sachau.deathcrawl.effects;


import net.sachau.deathcrawl.Event;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.gui.images.Tile;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

public abstract class CardEffect {

	private Tile tile;
	private int amount = 1;
	private Event ends;

	public CardEffect() {
		super();
	}
	abstract public void trigger(Card sourceCard, Card targetCard);
	abstract public void remove(Card card);

	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		} else {
			return o.getClass().equals(this.getClass());
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getClass());
	}
}
