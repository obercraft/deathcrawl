package net.sachau.deathcrawl;

import net.sachau.deathcrawl.dto.Game;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Game game = new Game();
		game.init();
		
		game.turn();
	}

}
