package net.sachau.deathcrawl.dto;

import javafx.beans.property.SimpleIntegerProperty;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.Deck;
import net.sachau.deathcrawl.cards.Hazard;
import net.sachau.deathcrawl.commands.CommandParser;
import net.sachau.deathcrawl.conditions.Conditions;
import net.sachau.deathcrawl.keywords.Keyword;

import java.util.HashSet;
import java.util.Set;


public class Player extends Creature {

	private Deck party;

	private Deck hand;

	private Deck discard;
	
	private Deck draw;

	private Deck hazard;

	private Turn turn;

	private int moves;

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

	@Override
	public int getAttackBonus() {
		return 0;
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
			for (Card c : getParty().getCards()) {
				if (c.getKeywords().contains(k)) {
					foundKeywords.add(k);
					if (keywords.size() == foundKeywords.size()) {
						return true;
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
}
