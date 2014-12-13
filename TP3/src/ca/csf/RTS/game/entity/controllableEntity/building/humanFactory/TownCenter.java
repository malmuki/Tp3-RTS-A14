package ca.csf.RTS.game.entity.controllableEntity.building.humanFactory;

import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.Team;
import ca.csf.RTS.game.entity.Tile;

public class TownCenter extends HumanFactory {

	private static final int MAX_HEALTH = 2000;
	private static final String NAME = "Town Center";

	public TownCenter(Tile originTile,Team team, GameEventHandler game)  {
		super(originTile, MAX_HEALTH , team, game);
	}

	@Override
	public void doTasks(float deltaTime) {
		
	}
	@Override
	public String getName() {
		return NAME;
	}
}
