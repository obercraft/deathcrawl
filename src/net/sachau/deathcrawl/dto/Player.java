package net.sachau.deathcrawl.dto;

import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.Deck;
import net.sachau.deathcrawl.cards.Hazard;
import net.sachau.deathcrawl.commands.CommandParser;


public class Player implements Creature {

	private Card classCard;

	private Deck permanent;
	
	private Deck hand;

	private Deck discard;
	
	private Deck draw;

	private Deck hazard;

	private Turn turn;

	private int moves;

	public Player(Card classCard) {
		super();

		this.classCard = classCard;
		this.classCard.setVisible(true);
		this.classCard.setOwner(this);

		permanent = new Deck();
		permanent.setVisible(true);

		draw = new Deck();
		draw.setVisible(false);

		hand = new Deck();
		hand.setVisible(true);

		discard = new Deck();
		discard.setVisible(true);

		turn = new Turn();
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

	public void endTurn() {

		if (hazard != null) {
			for (Card card : hazard.getCards()) {
				CommandParser.executeCommand(card, classCard);
			}
		}
	}

	public Deck getHazard() {
		return hazard;
	}

	public void setHazard(Deck hazard) {
		this.hazard = hazard;
	}

	public Card getClassCard() {
		return classCard;
	}

	public void setClassCard(Card classCard) {
		this.classCard = classCard;
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
