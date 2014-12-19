package ca.csf.RTS.game.entity.controllableEntity.human;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2i;

import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.Team;
import ca.csf.RTS.game.entity.Entity;
import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.controllableEntity.Trainee;
import ca.csf.RTS.game.entity.controllableEntity.building.WatchTower;
import ca.csf.RTS.game.entity.controllableEntity.building.factory.Barrack;
import ca.csf.RTS.game.entity.controllableEntity.building.factory.Forge;
import ca.csf.RTS.game.entity.controllableEntity.building.factory.TownCenter;
import ca.csf.RTS.game.entity.state.Building;
import ca.csf.RTS.game.entity.state.Gathering;
import ca.csf.RTS.game.entity.state.Idle;
import ca.csf.RTS.game.entity.state.Move;
import ca.csf.RTS.game.entity.state.State;
import ca.csf.RTS.game.pathFinding.DirectionFinder;
import ca.csf.RTS.game.pathFinding.PathFinder;

public class Worker extends Human {

	private static String TEXTURE_PATH = "./ressource/worker.png";
	private Texture texture;
	private static final String NAME = "Worker";
	private static final int RESSOURCE_SEARCH_RANGE = 35; // this accounts for
															// 10 per horizontal
															// move and 14 for
															// diagonal

	public Worker(Tile originTile, Team team, GameEventHandler game) {
		super(originTile, team, game, team.getWorkerModel().getHealthMax());
		try {
			if (texture == null) {
				texture = new Texture();
				texture.loadFromFile(Paths.get(TEXTURE_PATH));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		int row = 5;
		int column = 15;
		int height = 32;
		int width = 28;
		sprite.setTextureRect(new IntRect(column, row, width, height));
		sprite.setTexture(texture);
		setSpritePos();
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
					if (target != null) {
						stateStack.push(new Gathering(this));
					} else {
						stateStack.push(getDefaultState());
					}
				}

				break;
			case targetTooFar:
				stateStack.push(new Move(DirectionFinder.getClosestLocation(this), this));
				break;
			case targetUnreachable:

				break;
			case ressourceDepleted:
				stateStack.pop();
				setTarget(search());
				break;
			case dead:
				game.remove(this);
				break;
			default:
				break;
			}
		}
	}

	@Override
	public Entity search() {
		return PathFinder.findClosestRessource(this, RESSOURCE_SEARCH_RANGE);
	}

	@Override
	public String getName() {
		return NAME;
	}

	public void build(Trainee target) {
		stateStack.push(new Building(this, target));
	}

	public Trainee getBuildingOrder(int index) {
		switch (index) {
		case 0:
			return Trainee.TOWN_CENTER;
		case 1:
			return Trainee.BARRACK;
		case 2:
			return Trainee.FORGE;
		case 3:
			return Trainee.WATCH_TOWER;
		default:
			return null;
		}
	}

	public Vector2i getBuildingSize(Trainee trainee) {
		switch (trainee) {
		case BARRACK:
			return Barrack.DIMENSION;
		case TOWN_CENTER:
			return TownCenter.DIMENSION;
		case WATCH_TOWER:
			return WatchTower.DIMENSION;
		case FORGE:
			return Forge.DIMENSION;
		default:
			return null;
		}
	}
}
