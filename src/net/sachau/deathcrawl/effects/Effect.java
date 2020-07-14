package net.sachau.deathcrawl.effects;


import net.sachau.deathcrawl.cards.Card;

import java.io.Serializable;
import java.util.Set;

public abstract class Effect implements Serializable {

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

	abstract public void trigger(Card targetCard);

	public void triggerMany(Set<Card> cards) {
		if (cards != null) {
			for (Card card : cards) {
				trigger(card);
			}
		}

	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
	
	
	
	
}
