package ca.csf.RTS.game.entity.controllableEntity.building;

import java.util.ArrayList;

import ca.csf.RTS.game.entity.controllableEntity.ControlableEntity;
import ca.csf.RTS.game.model.Tile;

public abstract class Building extends ControlableEntity {
public Building(ArrayList<Tile> tiles,String name) {
	super( tiles, name);
}
}
