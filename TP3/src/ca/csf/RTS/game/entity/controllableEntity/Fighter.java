package ca.csf.RTS.game.entity.controllableEntity;

import org.jsfml.system.Vector2i;

import ca.csf.RTS.game.entity.Entity;

public interface Fighter {

	public void attack();

	public int getRange();

	public int getDamage();

	public Entity getTarget();

	public Vector2i getCenter();
	
	public float getAttackDelay();
}