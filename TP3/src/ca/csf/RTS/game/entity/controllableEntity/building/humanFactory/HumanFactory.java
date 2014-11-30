package ca.csf.RTS.game.entity.controllableEntity.building.humanFactory;

import java.util.ArrayList;

import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.controllableEntity.building.Building;

public abstract class HumanFactory extends Building {

	public HumanFactory(ArrayList<Tile> tiles, String name, int maxHealth) {
		super(tiles, name, maxHealth);
	}
}
