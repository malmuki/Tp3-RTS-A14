package ca.csf.RTS.game.entity.controllableEntity.building.humanFactory;

import java.util.ArrayList;

import ca.csf.RTS.game.model.Tile;

public class Barrack extends HumanFactory {
	
	private static final int MAX_HEALTH = 1000;
	private static final String NAME = "Barrack";

	public Barrack(ArrayList<Tile> tiles) {
		super(tiles, NAME, MAX_HEALTH);
	}
}
