package net.sachau.deathcrawl;

import java.util.LinkedList;
import java.util.List;

import net.sachau.deathcrawl.gui.CardHolder;

public class Deathcrawl {

    private static Deathcrawl instance;

    private List<CardHolder> cardHolders;


    private Deathcrawl() {
    	cardHolders = new LinkedList<>();
    }

    public static Deathcrawl getInstance(){
    		if (instance == null) {
    			instance = new Deathcrawl();
    		}
    		return instance;
    }

	public List<CardHolder> getCardHolders() {
		return cardHolders;
	}

	public void setCardHolders(List<CardHolder> cardHolders) {
		this.cardHolders = cardHolders;
	}


}
