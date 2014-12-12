package ca.csf.RTS.game.entity.controllableEntity.human;

import java.util.ArrayList;

import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.entity.Entity;
import ca.csf.RTS.game.entity.Team;
import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.state.Idle;
import ca.csf.RTS.game.entity.state.State;

public class Worker extends Human {
	private static final int MAX_HEALTH = 100;
	private static final String NAME = "Worker";

	public Worker(ArrayList<Tile> tiles, Team team, GameEventHandler game) {
		super(tiles, MAX_HEALTH, team, game);
	}

	@Override
	public State getDefaultState() {
		return new Idle();
	}

	@Override
	public void order(Entity target) {

	}

	@Override
	public void doTasks() {
		switch (stateStack.peek().action()) {
		case ended:
			stateStack.pop();
			if (stateStack.isEmpty()) {
				stateStack.push(getDefaultState());
			}

			break;
		case targetTooFar:

			break;
		case notEnoughRessources:

			break;
		case spaceIsOccupied:

			break;
		case targetUnreachable:

			break;
		default:
			break;
		}
	}

	@Override
	public Entity search() {
		// TODO: dijitre
		return null;
	}
	@Override
	public String getName() {
		return NAME;
	}
}
