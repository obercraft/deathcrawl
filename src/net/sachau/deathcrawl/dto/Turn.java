package net.sachau.deathcrawl.dto;

public class Turn {

	public enum Phase {
		PREPARE,
		EVENT,
		PLAY,
		DRAW,
	}

	private static final Phase phaseOrder [] = {
			Phase.PREPARE,
			Phase.EVENT,
			Phase.PLAY,
			Phase.DRAW,
	};

	private int turnNumber;

	private Phase currentPhase;
	int currentPhaseIndex;

	public Turn() {
		turnNumber = 1;
		currentPhase = Phase.PREPARE;
		currentPhaseIndex = getPhaseIndex(currentPhase);

	}

	private int getPhaseIndex(Phase phase) {
		for (int i = 0; i < phaseOrder.length; i++) {
			Phase p = phaseOrder[i];
			if (p == phase) {
				return i;
			}
		}
		return 0;
	}

	public void nextPhase() {
		if (currentPhaseIndex == phaseOrder.length -1) {
			currentPhaseIndex = 0;
		} else {
			currentPhaseIndex ++;
		}
		currentPhase = phaseOrder[currentPhaseIndex];
	}

	public int getTurnNumber() {
		return turnNumber;
	}

	public void setTurnNumber(int turnNumber) {
		this.turnNumber = turnNumber;
	}

	public void drawPhase() {
		
	}
}
