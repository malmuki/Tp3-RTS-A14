package ca.csf.RTS.game.entity.controllableEntity.human;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.Texture;

import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.Team;
import ca.csf.RTS.game.entity.Entity;
import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.state.Gathering;
import ca.csf.RTS.game.entity.state.Idle;
import ca.csf.RTS.game.entity.state.Move;
import ca.csf.RTS.game.entity.state.State;
import ca.csf.RTS.game.pathFinding.PathFinder;

public class Worker extends Human {

	static {
		try {
			texture = new Texture();
			texture.loadFromFile(Paths.get("./ressource/Soldat.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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
		stateStack.clear();
		if (target.getTeam().getName() == "Nature") {
			setTarget(target);
			stateStack.push(new Gathering(this));
		} else {
			stateStack.push(new Move(target.getTilesOrigin(), this));
		}
	}

	@Override
	public void doTasks(float deltaTime) {
		if (!stateStack.isEmpty()) {
			switch (stateStack.peek().action(deltaTime)) {
			case ended:
				stateStack.pop();
				if (stateStack.isEmpty()) {
					stateStack.push(getDefaultState());
				}

				break;
			case targetTooFar:
				stateStack.push(new Move(target.getTilesOrigin(), this));
				break;
			case notEnoughRessources:
				// afficher ou dire qu'il manque des ressource puis pop
				break;
			case spaceIsOccupied:
				// afficher ou dire qu'il manque de place puis pop
				break;
			case targetUnreachable:

				break;
			case ressourceDepleted:
				setTarget(search());
			default:
				break;
			}
		}
	}

	@Override
	public Entity search() {
		return PathFinder.findClosestRessource(this, 50);
	}

	@Override
	public String getName() {
		return NAME;
	}
}
