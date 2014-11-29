package ca.csf.RTS.game.entity.ressource;

import java.util.ArrayList;

import ca.csf.RTS.game.model.Tile;

public class Stone extends Ressource {
	
	private static final String NAME = "Stone";
	private static final int RESSOURCE_MAX = 1500;
	
	public Stone(ArrayList<Tile> tiles) {
		super( tiles, NAME, RESSOURCE_MAX);
	}
}
