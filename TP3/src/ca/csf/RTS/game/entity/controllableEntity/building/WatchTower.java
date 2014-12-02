package ca.csf.RTS.game.entity.controllableEntity.building;

import java.util.ArrayList;

import ca.csf.RTS.game.entity.Entity;
import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.controllableEntity.Fightable;
import ca.csf.RTS.game.entity.controllableEntity.Fighter;

public class WatchTower extends Building implements Fighter {
	
	private static final int MAX_HEALTH = 1000;
	private static final String NAME = "WatchTower";
	
	public WatchTower(ArrayList<Tile> tiles) {
		super(tiles, NAME, MAX_HEALTH);
	}

	@Override
	public void attack(Fightable target) {

	}
	
	public void order(Entity onTile) {
	  
	}

    public void order(Tile target) {}
}