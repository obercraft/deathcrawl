package net.sachau.deathcrawl.dto;

import javafx.beans.property.SimpleIntegerProperty;
import net.sachau.deathcrawl.*;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.Deck;
import net.sachau.deathcrawl.cards.catalog.Catalog;
import net.sachau.deathcrawl.cards.types.StartingCharacter;
import net.sachau.deathcrawl.cards.types.EventDeck;
import net.sachau.deathcrawl.effects.CardEffect;
import net.sachau.deathcrawl.gui.map.MapCoord;
import net.sachau.deathcrawl.keywords.Keyword;

import java.util.*;


public class Player extends Creature {

	private Deck party;

	private Deck hand;

	private SimpleIntegerProperty handSize = new SimpleIntegerProperty(5);

	private Deck discard;
	
	private Deck draw;

	private Deck hazard;

	private Turn turn;

	private int moves;

	private MapCoord mapCoord;

	private SimpleIntegerProperty momentum = new SimpleIntegerProperty(0);


	public Player() {
		super();

		party = new Deck();
		party.setVisible(true);

		draw = new Deck();
		draw.setVisible(false);

		hand = new Deck();
		hand.setVisible(true);

		discard = new Deck();
		discard.setVisible(true);

		turn = new Turn();

	}

	public Deck getHand() {
		return hand;
	}

	public void setHand(Deck hand) {
		this.hand = hand;
	}

	public Deck getDiscard() {
		return discard;
	}

	public void setDiscard(Deck discard) {
		this.discard = discard;
	}

	public Deck getDraw() {
		return draw;
	}

	public void setDraw(Deck draw) {
		this.draw = draw;
	}

	public int getMoves() {
		return moves;
	}

	public void setMoves(int moves) {
		this.moves = moves;
	}


	public void addToDeck(Card card) {
		draw.add(card);
		// TODO Auto-generated method stub
		
	}

	public Turn getTurn() {
		return turn;
	}

	public void setTurn(Turn turn) {
		this.turn = turn;
	}

	public Deck getHazard() {
		return hazard;
	}

	public void setHazard(Deck hazard) {
		this.hazard = hazard;
	}

	public Deck getParty() {
		return party;
	}

	public void setParty(Deck party) {
		this.party = party;
	}
/*
	public List<Card> drawCards(int numberOfCards) {
		List<Card> cards = new LinkedList<>();
		for (int i = 0; i< numberOfCards; i++) {
			if (draw.size() > 0) {
				cards.add(draw.draw());
			} else {
				List<Card> discardedCards = discard.getAll();
				if (discardedCards != null) {
					for (Card discarded : discardedCards) {
						draw.add(discarded);
					}
				}
				discard.clean();
				draw.shuffe();
				draw.draw();
			}
			
		}
		return cards;
		
	}
*/

	public boolean hasAllKeywords(Set<Keyword> keywords) {
		if (keywords ==  null) {
			return true;
		}
		Set<Keyword> foundKeywords = new HashSet<>();
		for (Keyword k : keywords) {

			for (Card card : getParty().getCards()) {
				if (card instanceof StartingCharacter) {
					StartingCharacter startingCharacter = (StartingCharacter) card;
					if (startingCharacter.getKeywords()
							.contains(k)) {
						foundKeywords.add(k);
						if (keywords.size() == foundKeywords.size()) {
							return true;
						}
					}
				}
			}
		}
		return false;
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

		if (amount - limit > 0) {
			getDiscard().moveAll(getDraw());
			getDraw().shuffe();
			getDraw().draw(getHand(), Math.min(getDraw().size(), amount - limit));
		}
	}

	public List<Card> getPartyCardsWithKeywords(Set<Keyword> wantedKeywords) {
		List<Card> cards = new LinkedList<>();
		for (Card card : getParty().getCards()) {
			if (card.hasAllKeywords(wantedKeywords)) {
				cards.add(card);
			}
		}
		return cards;
	}

	public boolean isAll(List<Card> partyMembers, Class<? extends CardEffect> effect) {
		for (Card card : partyMembers) {
			if (!card.hasCondition(effect)) {
				return false;
			}
		}
		return true;
	}

}
