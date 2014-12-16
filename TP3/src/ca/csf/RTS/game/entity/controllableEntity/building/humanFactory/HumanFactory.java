package ca.csf.RTS.game.entity.controllableEntity.building.humanFactory;

import org.jsfml.system.Vector2i;

import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.Team;
import ca.csf.RTS.game.entity.Entity;
import ca.csf.RTS.game.entity.GameObject;
import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.controllableEntity.building.Building;

public abstract class HumanFactory extends Building {
    protected GameObject rallyPoint;

	public HumanFactory(Tile originTile, int maxHealth, Team team, GameEventHandler game, Vector2i dimension) {
		super(originTile, maxHealth, team, game, dimension);
		rallyPoint = null;
	}
	
	public void order(Entity onTile) {
	  rallyPoint = onTile;
	}

    public void order(Tile target) {
      rallyPoint = target;
    }
}