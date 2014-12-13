package ca.csf.RTS.game.entity.controllableEntity.human;

import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.Team;
import ca.csf.RTS.game.entity.Entity;
import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.state.Gathering;
import ca.csf.RTS.game.entity.state.Idle;
import ca.csf.RTS.game.entity.state.Move;
import ca.csf.RTS.game.entity.state.State;

public class Worker extends Human {
	private static final int MAX_HEALTH = 100;
	private static final String NAME = "Worker";

	public Worker(Tile originTile, Team team, GameEventHandler game) {
		super(originTile, MAX_HEALTH, team, game);
	}

	@Override
	public State getDefaultState() {
		return new Idle();
	}

	@Override
	public void order(Entity target) {
		if (target.getTeam().getName() == "Nature") {
			setTarget(target);
			stateStack.push(new Gathering(this));
		} else {
			stateStack.push(new Move(target.getTilesOrigin(), this));
		}
	}

	@Override
	public void doTasks(float deltaTime) {
		switch (stateStack.peek().action(deltaTime)) {
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
