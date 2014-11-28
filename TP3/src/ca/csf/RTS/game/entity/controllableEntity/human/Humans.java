package ca.csf.RTS.game.entity.controllableEntity.human;

import java.util.ArrayList;

import ca.csf.RTS.game.entity.controllableEntity.ControlableEntity;
import ca.csf.RTS.game.model.Tile;

public abstract class Humans extends ControlableEntity {
	
	public Humans(ArrayList<Tile> tiles,String name, int maxHealth) {
		super( tiles, name, maxHealth );
	}
}
