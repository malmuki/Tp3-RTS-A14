package ca.csf.RTS.game.unitsModel;

public class WorkerModel extends Model {
	private int maxHealth = 80;

	public int getHealthMax() {
		return maxHealth;
	}

	public void upgradeHealthMax(int amount) {
		maxHealth += amount;
	}
}