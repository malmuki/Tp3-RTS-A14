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
import ca.csf.RTS.game.entity.state.Attack;
import ca.csf.RTS.game.entity.state.State;
import ca.csf.RTS.game.pathFinding.PathFinder;

public class WatchTower extends Building implements Fighter, Watcher {

	private static final int MAX_HEALTH = 350;
	private static final String NAME = "WatchTower";
	private static final int RANGE = 10;
	private static final int DAMAGE = 20;
	private static final float ATTACK_DELAY = 2.5f;
	private static final Vector2i DIMENSION = new Vector2i(3, 3);
	private static Texture texture;

	public WatchTower(Tile originTile, Team team, GameEventHandler game) {
		super(originTile, MAX_HEALTH, team, game, DIMENSION);
		try {
			if (texture == null) {
				texture = new Texture();
				texture.loadFromFile(Paths.get("./ressource/tower.png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		sprite.setTexture(texture);
		setSpritePos();
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
						stateStack.push(new Attack(this));
					} else {
						stateStack.push(getDefaultState());
					}
				}
				break;
			case targetSighted:
				stateStack.push(new Attack(this));
				break;

			case targetTooFar:
				stateStack.clear();
				stateStack.push(new Attack(this));
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

  @Override
  public float getAttackDelay() {
    return ATTACK_DELAY;
  }
}