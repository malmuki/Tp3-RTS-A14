package ca.csf.RTS.game.entity.controllableEntity.building.humanFactory;

import java.util.ArrayList;

import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.Team;
import ca.csf.RTS.game.entity.Entity;
import ca.csf.RTS.game.entity.GameObject;
import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.controllableEntity.building.Building;

public abstract class HumanFactory extends Building {
    protected GameObject rallyPoint;

	public HumanFactory(ArrayList<Tile> tiles, int maxHealth , Team team, GameEventHandler game) {
		super(tiles, maxHealth, team, game);
		rallyPoint = null;
	}
	
	public void order(Entity onTile) {
	  rallyPoint = onTile;
	}

    public void order(Tile target) {
      rallyPoint = target;
    }
}