package ca.csf.RTS.game.entity.controllableEntity.building.humanFactory;

import org.jsfml.system.Vector2i;

import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.Team;
import ca.csf.RTS.game.entity.Tile;

public class Barrack extends HumanFactory {
	
	private static final int MAX_HEALTH = 1000;
	private static final String NAME = "Barrack";
	private static final Vector2i DIMENSION = new Vector2i(6,6);

	public Barrack(Tile originTile, Team team, GameEventHandler game) {
		super(originTile, MAX_HEALTH , team, game, DIMENSION);
	}

	@Override
	public void doTasks(float deltaTime) {
		
	}

	@Override
	public String getName() {
		return NAME;
	}
}
