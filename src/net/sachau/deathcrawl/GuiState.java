package net.sachau.deathcrawl;

import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.Knife;
import net.sachau.deathcrawl.dto.Player;
import net.sachau.deathcrawl.dto.Turn;
import net.sachau.deathcrawl.gui.CardHolder;
import net.sachau.deathcrawl.gui.CardTile;
import net.sachau.deathcrawl.gui.DiscardPile;
import net.sachau.deathcrawl.gui.DrawPile;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GuiState {

    private static GuiState instance;

	private CardHolder handHolder;
	private DrawPile drawPile;
	private DiscardPile discardPile;
	private Map<Card, CardTile> cardTiles;


    private GuiState() {
    	handHolder = new CardHolder(6);
    	drawPile = new DrawPile();
    	discardPile = new DiscardPile();
    	cardTiles = new HashMap<>();
    }

    public static GuiState getInstance(){
    		if (instance == null) {
    			instance = new GuiState();
    		}
    		return instance;
    }

	public CardTile getCardTile(Card card) {
		CardTile cardTile = cardTiles.get(card);
		if (cardTile == null)  {
			cardTile = new CardTile(card);
			cardTiles.put(card, cardTile);
		}
		return cardTile;
	}

	public CardHolder getHandHolder() {
		return handHolder;
	}

	public DrawPile getDrawPile() {
		return drawPile;
	}

	public DiscardPile getDiscardPile() {
		return discardPile;
	}

	public Map<Card, CardTile> getCardTiles() {
		return cardTiles;
	}

	public void addCardTile(Card card) {
    	if (cardTiles.get(card) == null) {
    		CardTile cardTile = new CardTile(card);
    		cardTiles.put(card, cardTile);
		}

	}
}
