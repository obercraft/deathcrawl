package net.sachau.deathcrawl;

import java.util.*;

import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.Knife;
import net.sachau.deathcrawl.dto.Player;
import net.sachau.deathcrawl.dto.Turn;
import net.sachau.deathcrawl.gui.CardHolder;
import net.sachau.deathcrawl.gui.CardTile;
import net.sachau.deathcrawl.gui.DiscardPile;
import net.sachau.deathcrawl.gui.DrawPile;

public class GameState {

    private static GameState instance;

    private Player player;
    private Turn turn;

    long id = 1;

    private GameState() {
    	player = new Player();
    	turn = new Turn();
    }

    public static GameState getInstance(){
    		if (instance == null) {
    			instance = new GameState();
    		}
    		return instance;
    }

	public Player getPlayer() {
		return player;
	}

	public synchronized  long createId() {
    	return id ++;
	}

	public Turn getTurn() {
		return turn;
	}
}
