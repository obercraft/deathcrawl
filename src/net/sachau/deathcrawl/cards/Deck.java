package net.sachau.deathcrawl.cards;

import net.sachau.deathcrawl.effects.Effect;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Deck implements Serializable {

	List<Card> cards;
	private boolean visible;
		
	public Deck() {
		super();
		cards = new LinkedList<>();
	}

	public void add(Card card) {
		if (visible) {
			card.setVisible(true);
		}
		cards.add(card);
	}
	
	public void shuffe() {
		if (cards.size() == 0) {
			return;
		}
		Collections.shuffle(cards);
	}
	
	public Card draw(Deck targetDeck) {
		return draw(0, targetDeck);
	}

	public Card discard(Deck targetDeck) {
		return discard(0, targetDeck);
	}


	public Card drawRandom(Deck targetDeck) {
		int randomNum = ThreadLocalRandom.current().nextInt(0, cards.size());
		return draw(randomNum, targetDeck);
	}

	private Card draw(int topIndex, Deck targetDeck) {
		Card drawedCard = cards.size() > topIndex ? cards.get(topIndex) : null;
		if (drawedCard != null) {
			drawedCard.triggerPhaseEffects(Effect.Phase.DRAW);

		}
		targetDeck.add(drawedCard);
		remove(drawedCard);
		return drawedCard;
	}

	private Card discard(int topIndex, Deck targetDeck) {
		Card discardCard = cards.size() > topIndex ? cards.get(topIndex) : null;
		if (discardCard != null) {
			discardCard.triggerPhaseEffects(Effect.Phase.DISCARD);

		}
		targetDeck.add(discardCard);
		remove(discardCard);
		return discardCard;
	}

	public void remove(Card discardCard) {
		cards.remove(discardCard);
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


	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public void moveAll(Deck drawPile) {
		for (Card card : getAll()) {
			card.setVisible(drawPile.isVisible());
			drawPile.add(card);
		}
		cards.removeAll(cards);
	}
}
