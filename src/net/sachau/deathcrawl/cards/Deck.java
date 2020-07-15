package net.sachau.deathcrawl.cards;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.sachau.deathcrawl.effects.Effect;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Deck implements Serializable {

	private ListProperty<Card> cards;

	private SimpleBooleanProperty visible = new SimpleBooleanProperty();
		
	public Deck() {
		super();
		ObservableList<Card> observableList = FXCollections.observableArrayList(new ArrayList<>());
		this.cards = new SimpleListProperty<>(observableList);
	}

	public void add(Card card) {
		if (isVisible()) {
			card.setVisible(true);
		}
		card.setDeck(this);
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
		cards.remove(0, cards.size());

	}

	@Override
	public String toString() {
		return "Deck [cards=" + cards + "]";
	}


	public void moveAll(Deck drawPile) {
		for (Card card : getAll()) {
			card.setVisible(drawPile.isVisible());
			drawPile.add(card);
		}
		cards.removeAll(cards);
	}

	public ObservableList<Card> getCards() {
		return cards.get();
	}

	public ListProperty<Card> cardsProperty() {
		return cards;
	}

	public void setCards(ObservableList<Card> cards) {
		this.cards.set(cards);
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

	public void destroy(Card card) {
		cards.remove(card);
	}
}
