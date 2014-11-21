package ca.csf.RTS.game.model;

import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

public class Tile {
	
	public static final float TILE_SIZE = 20;
	
	private final Vector2i locationMap;
	private final Vector2f locationScreen;
	
	public Tile(Vector2i locationMap){
		this.locationMap = locationMap;
		this.locationScreen = new Vector2f(locationMap.x + TILE_SIZE/2, locationMap.y + TILE_SIZE/2);
	}
}
