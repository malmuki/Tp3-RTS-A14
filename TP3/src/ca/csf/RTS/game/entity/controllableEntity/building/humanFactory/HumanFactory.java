package ca.csf.RTS.game.entity.controllableEntity.building.humanFactory;

import java.util.ArrayList;

import ca.csf.RTS.game.entity.controllableEntity.building.Building;
import ca.csf.RTS.game.model.Tile;

public abstract class HumanFactory extends Building {

	public HumanFactory(ArrayList<Tile> tiles, String name) {
		super(tiles, name);
	}
}
