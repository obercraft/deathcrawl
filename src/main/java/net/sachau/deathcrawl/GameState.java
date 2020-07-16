package net.sachau.deathcrawl;

public class GameState {

    private static long id = 1;

	public static synchronized  long createId() {
    	return id ++;
	}
}
