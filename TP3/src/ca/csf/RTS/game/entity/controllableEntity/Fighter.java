package ca.csf.RTS.game.entity.controllableEntity;

public interface Fighter {
	
	public void attack(Fightable target);
	
	public int getRange();
	
	public int getDamage();
}
