package net.sachau.deathcrawl.effects;

import net.sachau.deathcrawl.GameState;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.dto.Player;

public class Move extends Effect {

	public Move() {
	}
	
	public Move(int amount) {
		super();
		this.amount = amount;
	}

	@Override
	public void trigger(Card target) {
		Player player = GameState.getInstance().getPlayer();
		int currentMoves = player.getMoves() + amount;
		player.setMoves(currentMoves);
		
	}


}
