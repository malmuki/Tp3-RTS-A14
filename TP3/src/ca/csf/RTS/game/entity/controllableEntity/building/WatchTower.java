package ca.csf.RTS.game.entity.controllableEntity.building;

import java.util.ArrayList;

import ca.csf.RTS.game.entity.controllableEntity.Fightable;
import ca.csf.RTS.game.entity.controllableEntity.Fighter;
import ca.csf.RTS.game.model.Tile;

public class WatchTower extends Building implements Fighter{
	public WatchTower(ArrayList<Tile> tiles, String name) {
		super(tiles, name);
	}

	@Override
	public void attack(Fightable target) {
		
	}
}
