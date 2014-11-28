package ca.csf.RTS.game.entity.controllableEntity.building;

import java.util.ArrayList;

import ca.csf.RTS.game.model.Tile;

public class Forge extends Building {

	private static final int MAX_HEALTH = 1000;
	private static final String NAME = "Forge";
	
	public Forge(ArrayList<Tile> tiles) {
		super( tiles, NAME, MAX_HEALTH);
	}
}
