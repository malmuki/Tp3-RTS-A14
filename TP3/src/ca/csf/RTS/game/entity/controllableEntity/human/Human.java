package ca.csf.RTS.game.entity.controllableEntity.human;

import java.util.ArrayList;

import org.jsfml.system.Vector2i;

import ca.csf.RTS.game.entity.Entity;
import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.controllableEntity.ControlableEntity;
import ca.csf.RTS.game.entity.state.Move;
import ca.csf.RTS.game.entity.state.State;

public abstract class Human extends ControlableEntity {
	
	public Human(ArrayList<Tile> tiles,String name, int maxHealth) {
		super( tiles, name, maxHealth );
	}
	
	public void order(Tile target) {
	  stateStack.clear();
	  stateStack.add(new Move(target, this));
	}
	
	public void order(Entity target) {
      //TODO: STUFF
    }
	
	public void move(Tile tile){
	  currentTiles.get(0).setOnTile(null);
	  currentTiles.clear();
	  currentTiles.add(tile);
	  tile.setOnTile(this);
	}
	
	 public abstract State getDefaultState();
}