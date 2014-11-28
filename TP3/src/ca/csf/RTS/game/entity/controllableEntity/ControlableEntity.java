package ca.csf.RTS.game.entity.controllableEntity;

import java.util.ArrayList;
import java.util.Stack;

import ca.csf.RTS.game.entity.GameEntity;
import ca.csf.RTS.game.entity.state.State;
import ca.csf.RTS.game.model.Tile;

public abstract class ControlableEntity extends GameEntity implements Fightable{

	protected final int healthMax;
	protected int healthCurrent;
	protected Stack<State> stateStack;
	
	public ControlableEntity(ArrayList<Tile> tiles,String name,int healthMax) {
		super( tiles, name);
		this.healthMax = healthMax;
		healthCurrent = healthMax;
	}
}
