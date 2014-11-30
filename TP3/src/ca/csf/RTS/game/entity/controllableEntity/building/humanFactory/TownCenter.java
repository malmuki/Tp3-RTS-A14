package ca.csf.RTS.game.entity.controllableEntity.building.humanFactory;

import java.util.ArrayList;

import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.controllableEntity.Fightable;
import ca.csf.RTS.game.entity.controllableEntity.Fighter;

public class TownCenter extends HumanFactory implements Fighter{

	private static final int MAX_HEALTH = 2000;
	private static final String NAME = "Town Center";

	public TownCenter(ArrayList<Tile> tiles)  {
		super(tiles, NAME, MAX_HEALTH);
	}

	@Override
	public void attack(Fightable target) {
		
	}
}
