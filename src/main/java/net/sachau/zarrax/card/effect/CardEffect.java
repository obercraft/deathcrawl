package net.sachau.zarrax.card.effect;


import net.sachau.zarrax.engine.GameEngine;
import net.sachau.zarrax.engine.GameEventContainer;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.gui.images.Tile;

import java.util.Objects;

public abstract class CardEffect {

	private long id;
	private Tile tile;
	private int amount = 1;
	private GameEventContainer ends;

	public CardEffect() {
		super();
		id = GameEngine.createId();
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
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CardEffect that = (CardEffect) o;
		return id == that.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public long getId() {
		return id;
	}

}
