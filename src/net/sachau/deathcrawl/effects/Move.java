package net.sachau.deathcrawl.effects;

import net.sachau.deathcrawl.dto.Player;

public class Move extends Effect {

	public Move() {
	}
	
	public Move(int amount) {
		super();
		this.amount = amount;
	}

	@Override
	public void trigger(Target target) {
		Player player = (Player) target;
		int currentMoves = player.getMoves() + amount;
		player.setMoves(currentMoves);
		
	}


}
