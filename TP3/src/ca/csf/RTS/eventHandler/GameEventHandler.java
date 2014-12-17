package ca.csf.RTS.eventHandler;

import ca.csf.RTS.game.entity.Entity;

public interface GameEventHandler {
	public void remove(Entity entity);
	
	public void add(Entity entity);
}
