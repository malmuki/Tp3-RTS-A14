package ca.csf.RTS.game.entity.controllableEntity.building.factory;

import org.jsfml.system.Vector2i;

import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.Team;
import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.state.State;

public class TownCenter extends Factory {

	private static final int MAX_HEALTH = 2000;
	private static final String NAME = "Town Center";
	private static final Vector2i DIMENSION = new Vector2i(8, 8);

	public TownCenter(Tile originTile, Team team, GameEventHandler game) {
		super(originTile, MAX_HEALTH, team, game, DIMENSION);
	}

	@Override
	public void doTasks(float deltaTime) {

	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public boolean spawnNext() {
		return false;
	}

	@Override
	protected State getDefaultState() {
		return null;
	}

	@Override
	protected Trainable getTrainable(int index) {
		return null;
	}
}
