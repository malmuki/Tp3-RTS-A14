package ca.csf.RTS.game.unitsModel;

public abstract class Model {
	protected int healthMax;

	public void upgradeHealthMax(int amount) {
		healthMax += amount;
	}

	public int getHealthMax() {
		return healthMax;
	}

}