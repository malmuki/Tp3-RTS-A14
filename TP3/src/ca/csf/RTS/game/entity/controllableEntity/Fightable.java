package ca.csf.RTS.game.entity.controllableEntity;

public interface Fightable {
	public void loseLife(int damage);

	public int getHP();

	public int getMaxHealth();
}
