package net.sachau.deathcrawl.cards;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Deck {

	List<Card> cards;
		
	public Deck() {
		super();
		cards = new LinkedList<>();
	}

	public void add(Card card) {
		cards.add(card);
	}
	
	public void shuffe() {
		if (cards.size() == 0) {
			return;
		}
		Collections.shuffle(cards);
	}
	
	public Card draw() {
		Card drawedCard = cards.size() > 0 ? cards.get(0) : null;
		if (drawedCard != null) {
			// TODO implement draw effect
			// List<Effect> effects = drawedCard.getPhaseEffects(Phase.DRAW);

		}
		return drawedCard;
	}
	
	public int size() {
		return cards.size();
	}

	public List<Card> getAll() {
		return cards;
		
	}

	public void clean() {
		cards = new LinkedList<>(); 

	}

	@Override
	public String toString() {
		return "Deck [cards=" + cards + "]";
	}
	
	
}
