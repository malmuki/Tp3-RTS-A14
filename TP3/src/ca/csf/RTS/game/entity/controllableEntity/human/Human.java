package ca.csf.RTS.game.entity.controllableEntity.human;

import org.jsfml.system.Vector2i;

import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.Team;
import ca.csf.RTS.game.entity.Entity;
import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.controllableEntity.ControlableEntity;
import ca.csf.RTS.game.entity.controllableEntity.Watcher;
import ca.csf.RTS.game.entity.state.Move;
import ca.csf.RTS.game.entity.state.State;

public abstract class Human extends ControlableEntity implements Watcher {

	public static final float MOVE_DELAY = 0.18f;

	public Human(Tile originTile, Team team, GameEventHandler game, int healthMax) {
		super(originTile, team, game, healthMax);
	}

	public void order(Tile target) {
		stateStack.clear();
		setTarget(null);
		stateStack.add(new Move(target, this));
	}

	public boolean moveToTile(Tile targetTile, float moveProgression) {
		if (moveProgression > MOVE_DELAY) {
			moveProgression = MOVE_DELAY;
		}
		sprite.setPosition(tilesOrigin.getScreenLocation().x + (targetTile.getScreenLocation().x - tilesOrigin.getScreenLocation().x) * moveProgression
				/ MOVE_DELAY, tilesOrigin.getScreenLocation().y + (targetTile.getScreenLocation().y - tilesOrigin.getScreenLocation().y) * moveProgression
				/ MOVE_DELAY);
		if (moveProgression >= MOVE_DELAY) {
			tilesOrigin.setOnTile(null);
			tilesOrigin = targetTile;
			targetTile.setOnTile(this);
			return true;
		}
		return false;
	}

	@Override
	public boolean isObstacle() {
		return false;
	}

	@Override
	public Vector2i getCenter() {
		return tilesOrigin.getMapLocation();
	}

	public abstract void order(Entity target);

	public abstract State getDefaultState();
}
