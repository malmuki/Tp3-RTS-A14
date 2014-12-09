package ca.csf.RTS.game.entity.controllableEntity.building.humanFactory;

import java.util.ArrayList;

import ca.csf.RTS.game.entity.Team;
import ca.csf.RTS.game.entity.Tile;

public class TownCenter extends HumanFactory {

	private static final int MAX_HEALTH = 2000;
	private static final String NAME = "Town Center";

	public TownCenter(ArrayList<Tile> tiles,Team team)  {
		super(tiles, NAME, MAX_HEALTH , team);
	}

	@Override
	public void doTasks() {
		
	}
}