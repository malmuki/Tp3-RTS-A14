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
			//dijtra
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
