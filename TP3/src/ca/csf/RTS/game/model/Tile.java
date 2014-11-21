package ca.csf.RTS.game.model;

import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

public class Tile {

	public static final float TILE_SIZE = 20;

	private final Vector2i mapLocation;
	private final Vector2f screenLocation;

	public Tile(Vector2i mapLocation) {
		this.mapLocation = mapLocation;
		this.screenLocation = new Vector2f(mapLocation.x * TILE_SIZE
				+ TILE_SIZE / 2, mapLocation.y * TILE_SIZE + TILE_SIZE / 2);
	}

	public Vector2f getScreenLocation() {
		return screenLocation;
	}

	public Vector2i getMapLocation() {
		return mapLocation;
	}

}
