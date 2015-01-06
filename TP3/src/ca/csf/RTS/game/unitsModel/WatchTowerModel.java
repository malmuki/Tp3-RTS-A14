package ca.csf.RTS.game.unitsModel;

public class WatchTowerModel extends Model {

	public WatchTowerModel() {
		healthMax = 450;
	}

	private int range = 10;
	private int damage = 25;
	private float attackDelay = 2.5f;

	public int getRange() {
		return range;
	}

	public int getDamage() {
		return damage;
	}

	public float getAttackDelay() {
		return attackDelay;
	}

	public void upgradeRange(int amount) {
		range += amount;
	}

	public void upgradeDamage(int amount) {
		damage += amount;
	}

	public void upgradeAttackDelay(float amount) {
		attackDelay -= amount;
	}
}