package ca.csf.RTS.game.entity.controllableEntity.building.humanFactory;

import java.util.ArrayList;

import ca.csf.RTS.game.entity.GameObject;
import ca.csf.RTS.game.entity.Entity;
import ca.csf.RTS.game.entity.Team;
import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.controllableEntity.building.Building;

public abstract class HumanFactory extends Building {
    protected GameObject rallyPoint;

	public HumanFactory(ArrayList<Tile> tiles, String name, int maxHealth , Team team) {
		super(tiles, name, maxHealth, team);
		rallyPoint = null;
	}
	
	public void order(Entity onTile) {
	  rallyPoint = onTile;
	}

    public void order(Tile target) {
      rallyPoint = target;
    }
}