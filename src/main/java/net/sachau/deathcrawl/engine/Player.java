package net.sachau.deathcrawl.engine;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.sachau.deathcrawl.card.Card;
import net.sachau.deathcrawl.card.Creature;
import net.sachau.deathcrawl.card.Deck;
import net.sachau.deathcrawl.card.keyword.Keyword;
import net.sachau.deathcrawl.gui.map.MapCoord;


public class Player extends Creature {

	private SimpleListProperty<Card> party;

	private SimpleListProperty<Card> hand;

	private SimpleIntegerProperty handSize = new SimpleIntegerProperty(5);

	private Deck draw;

	private SimpleListProperty<Card> hazards;

	private Turn turn;

	private int moves;

	private MapCoord mapCoord;

	private SimpleIntegerProperty momentum = new SimpleIntegerProperty(0);


	public Player() {
		super();

		party = new SimpleListProperty<>(FXCollections.observableArrayList());
		hand = new SimpleListProperty<>(FXCollections.observableArrayList());
		hazards = new SimpleListProperty<>(FXCollections.observableArrayList());

		draw = new Deck();
		draw.setVisible(false);

		turn = new Turn();

	}

	public int getMomentum() {
		return momentum.get();
	}

	public SimpleIntegerProperty momentumProperty() {
		return momentum;
	}

	public void setMomentum(int momentum) {
		this.momentum.set(momentum);
	}

	public int getHandSize() {
		return handSize.get();
	}

	public SimpleIntegerProperty handSizeProperty() {
		return handSize;
	}

	public void setHandSize(int handSize) {
		this.handSize.set(handSize);
	}

	public void initHand() {
		getDraw().draw(getHand(), getHandSize());
		for (Card card : getHand()) {
			card.setSource(Card.Source.HAND);
		}
	}

	public MapCoord getMapCoord() {
		return mapCoord;
	}

	public void setMapCoord(MapCoord mapCoord) {
		this.mapCoord = mapCoord;
	}

	public void draw(int amount) {
		if (amount < 1) {
			amount = 1;
		}
		int limit = Math.min(amount, getDraw().size());
		getDraw().draw(getHand(), limit);

	}


	public ObservableList<Card> getParty() {
		return party.get();
	}

	public SimpleListProperty<Card> partyProperty() {
		return party;
	}

	public void setParty(ObservableList<Card> party) {
		this.party.set(party);
	}

	public ObservableList<Card> getHand() {
		return hand.get();
	}

	public SimpleListProperty<Card> handProperty() {
		return hand;
	}

	public void setHand(ObservableList<Card> hand) {
		this.hand.set(hand);
	}

	public Deck getDraw() {
		return draw;
	}

	public void setDraw(Deck draw) {
		this.draw = draw;
	}

	public ObservableList<Card> getHazards() {
		return hazards.get();
	}

	public SimpleListProperty<Card> hazardsProperty() {
		return hazards;
	}

	public void setHazards(ObservableList<Card> hazards) {
		this.hazards.set(hazards);
	}

	public Turn getTurn() {
		return turn;
	}

	public void setTurn(Turn turn) {
		this.turn = turn;
	}

	public int getMoves() {
		return moves;
	}

	public void setMoves(int moves) {
		this.moves = moves;
	}

	public void addCardToHand(Card card) {
		card.setOwner(this);
		getHand().add(card);

	}

	public void discard(Card source) {
		Card toDiscard = null;
		for (Card card : getHand()) {
			if (source == card) {
				toDiscard = card;
			}
		}
		if (toDiscard != null) {
			getHand().remove(toDiscard);
			getDraw().addToDiscard(toDiscard);
		}
	}

	public void addToParty(Card card) {
		card.setOwner(this);
		card.addKeywords(Keyword.PERMANENT);
		this.getParty().add(card);

	}

	public void addToHazards(Card card) {
		card.setOwner(null);
		this.getHazards().add(card);
	}
}
