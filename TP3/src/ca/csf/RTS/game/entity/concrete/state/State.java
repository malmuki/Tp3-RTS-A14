package ca.csf.RTS.game.entity.concrete.state;

import ca.csf.RTS.game.entity.GameEntity;

public interface State {
	
	public void action(GameEntity gameEntity);
}