package ca.csf.RTS.eventHandler;

import java.util.ArrayList;

import ca.csf.RTS.game.entity.GameEntity;

public interface GameEventHandler {
	
	public void highlightSelected(ArrayList<GameEntity> gameEntity);
	
}
