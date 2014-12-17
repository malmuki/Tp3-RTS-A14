package ca.csf.RTS.game.entity.controllableEntity.building;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2i;

import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.Team;
import ca.csf.RTS.game.entity.Entity;
import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.controllableEntity.Fightable;
import ca.csf.RTS.game.entity.controllableEntity.Fighter;
import ca.csf.RTS.game.entity.controllableEntity.Watcher;
import ca.csf.RTS.game.entity.state.Alert;
import ca.csf.RTS.game.entity.state.RangeAttack;
import ca.csf.RTS.game.entity.state.State;
import ca.csf.RTS.game.pathFinding.PathFinder;

public class WatchTower extends Building implements Fighter,Watcher {
	
	static {
		try {
			texture = new Texture();
			texture.loadFromFile(Paths.get("./ressource/tower.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static final int MAX_HEALTH = 100;
	private static final String NAME = "WatchTower";
	private static final int RANGE = 10;
	private static final int DAMAGE = 1;
	private static final Vector2i DIMENSION = new Vector2i(3,3);

	public WatchTower(Tile originTile, Team team, GameEventHandler game) {
		super(originTile, MAX_HEALTH, team, game, DIMENSION);
	}

	@Override
	public void attack() {
		((Fightable) target).loseLife(DAMAGE);
	}

	public void order(Entity onTile) {

	}

	public void order(Tile target) {
	}

	@Override
	public void doTasks(float deltaTime) {
		if (!stateStack.isEmpty()) {

			switch (stateStack.peek().action(deltaTime)) {

			case notFinished:
			case noTargetSighted:
				break;

			case ended:
				stateStack.pop();

				if (stateStack.isEmpty()) {
					if (target != null) {
						stateStack.push(new RangeAttack(this));
					} else {
						stateStack.push(getDefaultState());
					}
				}
				break;
			case targetSighted:
				stateStack.push(new RangeAttack(this));
				break;

			case targetTooFar:
					stateStack.clear();
					stateStack.push(new RangeAttack(this));
				break;

			case dead:
				game.remove(this);
				break;

			default:
				break;
			}
		} else {
			stateStack.push(getDefaultState());
		}
	}

	private State getDefaultState() {
		return new Alert(this);
	}

	@Override
	public Entity search() {
		return PathFinder.findClosestEnnemy(this, RANGE);
	}

	@Override
	public int getRange() {
		return RANGE;
	}

	@Override
	public int getDamage() {
		return DAMAGE;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public Vector2i getCenter() {
		return Vector2i.add(tilesOrigin.getMapLocation(), new Vector2i(1, 1));
	}
}