package ca.csf.RTS.game.entity.controllableEntity.building.humanFactory;

import java.util.ArrayList;

import ca.csf.RTS.game.entity.GameObject;
import ca.csf.RTS.game.entity.Entity;
import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.controllableEntity.building.Building;

public abstract class HumanFactory extends Building {
    protected GameObject rallyPoint;

	public HumanFactory(ArrayList<Tile> tiles, String name, int maxHealth) {
		super(tiles, name, maxHealth);
		rallyPoint = null;
	}
	
	public void order(Entity onTile) {
	  rallyPoint = onTile;
	}

    public void order(Tile target) {
      rallyPoint = target;
    }
}