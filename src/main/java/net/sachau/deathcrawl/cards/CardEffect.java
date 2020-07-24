package net.sachau.deathcrawl.cards;


import net.sachau.deathcrawl.cards.Card;

import java.io.Serializable;
import java.util.Set;

public abstract class CardEffect implements Serializable {

	private String text;
	
	int amount = 1;
	
	
	
	public CardEffect() {
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
