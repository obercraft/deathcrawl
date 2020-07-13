package net.sachau.deathcrawl.dto;

import java.util.LinkedList;
import java.util.List;

import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.Deck;
import net.sachau.deathcrawl.effects.Target;

public class Player implements Target {

	private List<Card> permanent;
	
	private List<Card> hand;
	
	private Deck discard;
	
	private Deck draw;
	
	private int moves;

	public Player() {
		super();
		permanent = new LinkedList<>();
		// TODO Auto-generated constructor stub
	}

	public void addPermanent(Card card) {
		permanent.add(card);
	}
	
	public List<Card> getPermanent() {
		return permanent;
	}

	public void setPermanent(List<Card> permanent) {
		this.permanent = permanent;
	}

	public List<Card> getHand() {
		return hand;
	}

	public void setHand(List<Card> hand) {
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

	@Override
	public void damage(int amount) {
		// TODO Auto-generated method stub
		
	}

	public void addToDeck(Card card) {
		draw.add(card);
		// TODO Auto-generated method stub
		
	}

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
	
}
