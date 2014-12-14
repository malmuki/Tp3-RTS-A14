package ca.csf.RTS.game.entity.controllableEntity;

import ca.csf.RTS.game.entity.GameObject;

public interface Fighter {
	
	public void attack();
	
	public int getRange();
	
	public int getDamage();
	
	public GameObject getTarget();
}