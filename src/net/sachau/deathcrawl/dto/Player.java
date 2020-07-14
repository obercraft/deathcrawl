package net.sachau.deathcrawl.dto;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.Deck;
import net.sachau.deathcrawl.gui.CardHolder;
import net.sachau.deathcrawl.gui.DrawPile;


public class Player implements Serializable {

	private Deck permanent;
	
	private Deck hand;

	private Deck discard;
	
	private Deck draw;

	private int moves;

	public Player() {
		super();

		permanent = new Deck();
		permanent.setVisible(true);

		draw = new Deck();
		draw.setVisible(false);

		hand = new Deck();
		hand.setVisible(true);

		discard = new Deck();
		discard.setVisible(true);
	}

	public void addPermanent(Card card) {
		permanent.add(card);
	}
	
	public Deck getPermanent() {
		return permanent;
	}

	public void setPermanent(Deck permanent) {
		this.permanent = permanent;
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


	@Override
	public String toString() {
		return "Player [permanent=" + permanent + ", hand=" + hand
				+ ", discard=" + discard + ", draw=" + draw + ", moves="
				+ moves + "]";
	}

	public void addToDeck(Card card) {
		draw.add(card);
		// TODO Auto-generated method stub
		
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
}
