package ca.csf.RTS.game.entity.controllableEntity;

import java.util.ArrayList;

import ca.csf.RTS.game.entity.GameEntity;
import ca.csf.RTS.game.model.Tile;

public abstract class ControlableEntity extends GameEntity implements Fightable{

	public ControlableEntity(ArrayList<Tile> tiles,String name) {
		super( tiles, name);
	}
}
