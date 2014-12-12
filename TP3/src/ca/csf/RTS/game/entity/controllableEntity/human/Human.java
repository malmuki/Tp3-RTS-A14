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

	public Human(ArrayList<Tile> tiles, String name, int maxHealth, Team team, GameEventHandler game) {
		super(tiles, name, maxHealth , team, game);
	}

	public void order(Tile target) {
		stateStack.clear();
		stateStack.add(new Move(target, this));
	}
	
	public void moveToTile(Tile targetTile){
	  currentTiles.remove(0).setOnTile(null);
	  currentTiles.add(targetTile);
	  targetTile.setOnTile(this);
	  sprite.setPosition(targetTile.getScreenLocation());
	}
	@Override
	public boolean isObstacle() {
		return false;
	}

	public abstract void order(Entity target);

	public abstract State getDefaultState();
}
