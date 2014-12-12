package ca.csf.RTS.game.entity.controllableEntity.human;

import java.util.ArrayList;

import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.entity.Entity;
import ca.csf.RTS.game.entity.Team;
import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.controllableEntity.ControlableEntity;
import ca.csf.RTS.game.entity.controllableEntity.Watcher;
import ca.csf.RTS.game.entity.state.Move;
import ca.csf.RTS.game.entity.state.State;

public abstract class Human extends ControlableEntity implements Watcher {
	
	private static final float MOVE_DELAY = 0.18f;

	public Human(ArrayList<Tile> tiles, int maxHealth, Team team, GameEventHandler game) {
		super(tiles, maxHealth , team, game);
	public Human(ArrayList<Tile> tiles, String name, int maxHealth, Team team,
			GameEventHandler game) {
		super(tiles, name, maxHealth, team, game);
	}

	public void order(Tile target) {
		stateStack.clear();
		stateStack.add(new Move(target, this));
	}
	
	public boolean moveToTile(Tile targetTile, float moveProgression) {
		sprite.setPosition(
				currentTiles.get(0).getScreenLocation().x
						+ (targetTile.getScreenLocation().x - currentTiles.get(
								0).getScreenLocation().x) * moveProgression / MOVE_DELAY,
				currentTiles.get(0).getScreenLocation().y
						+ (targetTile.getScreenLocation().y - currentTiles.get(
								0).getScreenLocation().y) * moveProgression / MOVE_DELAY);
		if (moveProgression >= MOVE_DELAY) {
			currentTiles.remove(0).setOnTile(null);
			currentTiles.add(targetTile);
			targetTile.setOnTile(this);
			return true;
		}
		return false;
	}

	@Override
	public boolean isObstacle() {
		return false;
	}

	public abstract void order(Entity target);

	public abstract State getDefaultState();
}
