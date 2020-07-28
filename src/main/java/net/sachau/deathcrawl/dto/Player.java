package net.sachau.deathcrawl.dto;

import javafx.beans.property.SimpleIntegerProperty;
import net.sachau.deathcrawl.*;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.Deck;
import net.sachau.deathcrawl.cards.catalog.Catalog;
import net.sachau.deathcrawl.cards.types.StartingCharacter;
import net.sachau.deathcrawl.cards.types.EventDeck;
import net.sachau.deathcrawl.gui.map.MapCoord;
import net.sachau.deathcrawl.keywords.Keyword;

import java.util.*;


public class Player extends Creature implements Observer {

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
		Game.events().addObserver(this);

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

	@Override
	public void update(Observable o, Object arg) {
		switch(Game.get(arg)) {
			case STARTTURN:
				setMoves(1);

				// trigger events
				for (Card card : getParty().getCards()) {
					card.triggerPhaseEffects(Event.STARTTURN);
				}

				return;
			case STARTENCOUNTER:

				List<Card> eventCards = Catalog.getInstance().get(EventDeck.class);

				Deck hazards = new Deck();
				hazards.setVisible(true);
				for (Card card : eventCards.get(0).getDeck().getCards()) {
					try {
						hazards.add(Utils.copyCard(card));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				setHazard(hazards);

				for (Card card : getParty().getCards()) {
					card.triggerPhaseEffects(Event.STARTENCOUNTER);
				}

				for (Card card : getHazard().getCards()) {
					card.triggerPhaseEffects(Event.STARTENCOUNTER);
				}

				Game.events().send(Event.STARTENCOUNTERVIEW);
				Game.events().send(Event.STARTCARDPHASE);
				return;

			case PARTYDONE:
				for (Card c : getParty().getCards()) {
					StartingCharacter startingCharacter = (StartingCharacter) c;
					for (Card startingCard : startingCharacter.getStartingCards().getCards()) {
						startingCard.setOwner(this);
						getDraw().add(startingCard);
					}
				}

				initHand();
				return;
			case ENDCARDPHASE:
				getDraw().draw(getHand(), getHandSize() - getHand().size());

				// the hand still has open slots
				if (getHandSize() - getHand().size() > 0) {

					// put discard to draw pile and shuffle
					getDiscard().moveAll(getDraw());
					getDraw().shuffe();
					getDraw().draw(getHand(), getHandSize() - getHand().size());

					// draw again
					getDraw().draw(getHand(), getHandSize() - getHand().size());
				}
				if (getHazard().size() > 0) {
					GameAI.execute(this);
					Game.events()
							.send(Event.STARTCARDPHASE);
				} else {
					//GameEvent.events()
					//		.send(GameEvent.Type.EXPERIENCEPHASE);
					Game.events().send(Event.STARTTURN);
				}
				return;
			case CHARACTERDEATH:
				int totalHealth = 0;
				for (Card card : getParty().getCards()) {
					if (card instanceof StartingCharacter) {
						totalHealth += Math.max(0,card.getHits());
					}
				}
				// ALL DEAD
				if (totalHealth <= 0) {
					Logger.debug("all characters are dead - game over");
					Game.events().send(Event.GAMEOVER);
					return;
				}

		}
	}

	private void initHand() {
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
}
