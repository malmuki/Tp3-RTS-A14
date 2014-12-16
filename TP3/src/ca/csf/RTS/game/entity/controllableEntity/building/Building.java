package ca.csf.RTS.game.entity.controllableEntity.building;

import org.jsfml.system.Vector2i;

import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.Team;
import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.controllableEntity.ControlableEntity;

public abstract class Building extends ControlableEntity {
	public Building(Tile originTile, int maxHealth, Team team, GameEventHandler game, Vector2i dimension) {
		super(originTile, maxHealth, team, game);
		this.dimensions = dimension;
	}
	
	@Override
	public boolean isObstacle() {
		return true;
	}
}