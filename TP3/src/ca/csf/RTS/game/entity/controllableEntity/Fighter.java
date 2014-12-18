package ca.csf.RTS.game.entity.controllableEntity;

import ca.csf.RTS.game.entity.Entity;

public interface Fighter {

	public void attack();

	public int getRange();

	public int getDamage();

	public Entity getTarget();

	public float getAttackDelay();
}