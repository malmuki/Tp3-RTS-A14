package ca.csf.RTS.game.entity.controllableEntity.building;

import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.Team;
import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.controllableEntity.ControlableEntity;

public abstract class Building extends ControlableEntity {
	public Building(Tile originTile, int maxHealth, Team team, GameEventHandler game) {
		super(originTile, maxHealth, team, game);
	}
	
	@Override
	public boolean isObstacle() {
		return true;
	}
}