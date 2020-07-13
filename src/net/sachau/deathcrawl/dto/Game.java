package net.sachau.deathcrawl.dto;

import java.util.LinkedList;
import java.util.List;

import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.Goblin;
import net.sachau.deathcrawl.cards.Knife;
import net.sachau.deathcrawl.cards.Warrior;
import net.sachau.deathcrawl.effects.Effect;
import net.sachau.deathcrawl.effects.Effect.Phase;

public class Game {

	Player player;
	
	List<Card> hazards;
	
	
	public void init() {
		player = new Player();
		hazards = new LinkedList<>();
		player.addPermanent(new Warrior());
		player.addToDeck(new Knife());
		player.addToDeck(new Knife());
		player.addToDeck(new Knife());
		player.addToDeck(new Knife());
		player.addToDeck(new Knife());
		
		hazards.add(new Goblin());
	}
	
	public void turn() {
		preparePhase();
		//
		drawPhase();
		endPhase();
	}
	
	private void preparePhase() {
		// all permanent cards PREPARE events are triggered
		
		List<Card> cards = player.getPermanent();
		if (cards != null) {
			for (Card card : cards) {
				List<Effect> effects = card.getPhaseEffects(Phase.PREPARE);
				if (effects != null) {
					for (Effect effect : effects) {
						effect.trigger(player);
					}
				}
			}
		}
	}
	
	public void drawPhase() {
		int handsize = 5;
		player.drawCards(handsize);
		
		
		
	}
	
	public void eventPhase() {
		
	}
	
	private void endPhase() {
		System.out.println(player);
		player.setMoves(0);
	}
	
}
