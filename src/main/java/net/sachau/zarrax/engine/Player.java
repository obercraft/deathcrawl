package net.sachau.zarrax.engine;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.sachau.zarrax.Logger;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.Creature;
import net.sachau.zarrax.card.Deck;
import net.sachau.zarrax.card.UniqueCardList;
import net.sachau.zarrax.card.command.CommandResult;
import net.sachau.zarrax.card.keyword.Keyword;
import net.sachau.zarrax.card.type.Character;
import net.sachau.zarrax.gui.map.MapCoord;
import net.sachau.zarrax.map.LandType;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;


public class Player extends Creature {

	public final static int PARTY_SIZE = 4;
	public final static int MOVES = 4;

	private SimpleListProperty<Card> party;

	private SimpleListProperty<Card> hand;

	private SimpleIntegerProperty maxHandsize = new SimpleIntegerProperty(5);

	private Deck draw;

	private SimpleListProperty<Card> hazards;

	private Turn turn;

	private SimpleIntegerProperty moves = new SimpleIntegerProperty(MOVES);

	private MapCoord mapCoord;

	private UniqueCardList spawnCards;

	private SimpleIntegerProperty momentum = new SimpleIntegerProperty(0);




	public Player() {
		super();

		party = new SimpleListProperty<>(FXCollections.observableArrayList(new UniqueCardList()));
		hand = new SimpleListProperty<>(FXCollections.observableArrayList());
		hazards = new SimpleListProperty<>(FXCollections.observableArrayList(new UniqueCardList()));

		draw = new Deck();
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

	public int getMaxHandsize() {
		return maxHandsize.get();
	}

	public SimpleIntegerProperty maxHandsizeProperty() {
		return maxHandsize;
	}

	public void setMaxHandsize(int maxHandsize) {
		this.maxHandsize.set(maxHandsize);
	}

	public void initHand() {
		if (getHand().size() == 0) {
			int max = Math.min(getMaxHandsize(), draw.size() + draw.getDiscards()
					.size());
			for (int i = 0; i < max; i++) {
				Card card = draw.draw();
				card.setSource(Card.Source.HAND);
				card.setOwner(this);
				card.setVisible(true);

			}
		}
	}

	public MapCoord getMapCoord() {
		return mapCoord;
	}

	public void setMapCoord(MapCoord mapCoord) {
		this.mapCoord = mapCoord;
	}

	public Card draw() {
		return getDraw().draw();
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
		return moves.get();
	}

	public SimpleIntegerProperty movesProperty() {
		return moves;
	}

	public void setMoves(int moves) {
		this.moves.set(moves);
	}

	public void addCardToHand(Card card) {
		card.setOwner(this);
		card.setVisible(true);
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
			getDraw().discard(toDiscard);
		}
	}

	public boolean addToParty(Card card) {
		if (!StringUtils.isEmpty(card.getUniqueId())) {
			for (Card c : getParty()) {
				c.setVisible(true);
				c.setOwner(this);
				if (card.getUniqueId().toLowerCase().equalsIgnoreCase(c.getUniqueId())) {
					Logger.debug("already contains a " + card.getUniqueId());
					return false;
				}
			}
		}
		card.setOwner(this);
		card.addKeyword(Keyword.PERMANENT);
		this.getParty().add(card);
		return true;
	}

	public void addToHazards(Card card) {
		card.setOwner(null);
		this.getHazards().add(card);
	}

	public UniqueCardList getSpawnCards() {
		return spawnCards;
	}

	public void setSpawnCards(UniqueCardList spawnCards) {
		this.spawnCards = spawnCards;
	}

	public void addCardToDraw(Card card) {
		card.setOwner(this);
		card.setVisible(false);
		draw.add(card);
		Collections.shuffle(draw);
	}

	public void resetHand() {
		int cardsToDraw = Math.min(getMaxHandsize() - this.getHand().size(), draw.size() + draw.getDiscards().size());
		for (int i = 0; i< cardsToDraw; i++) {
			Card card = draw.draw();
			addCardToHand(card);
		}
	}


	public CommandResult longRest(MapCoord mapCoord) {
		int moves = getMoves();
		if (moves < 4) {
			return CommandResult.notAllowed("not enough moves");
		} else {
			for (Card card : getParty()) {
				if (card instanceof Character) {
					card.longRest();
				}
			}
			return CommandResult.successful();
		}
	}

	public CommandResult move(LandType targetLand) {
		int moves = getMoves();
		switch (targetLand) {
			default:
			case VALLEY:
				if (moves < 1) {
					return CommandResult.notAllowed("not enough moves");
				}
				setMoves(moves -1);
				return CommandResult.successful();

			case HILL:
			case WATER:
			case MOUNTAINS:
			case WOODS:
				if (moves < 2) {
					return CommandResult.notAllowed("not enough moves");
				}
				setMoves(moves -2);
				return CommandResult.successful();
		}
	}
}
