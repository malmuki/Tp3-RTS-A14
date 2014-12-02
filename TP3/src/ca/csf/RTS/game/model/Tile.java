package ca.csf.RTS.game.model;

import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import ca.csf.RTS.entity.Entity;

public class Tile {

	public static final float TILE_SIZE = 20;

	private final Vector2i mapLocation;
	private final Vector2f screenLocation;
	private Entity onTile;

	public Tile(Vector2i mapLocation) {
		setOnTile(null);
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

	public Entity getOnTile() {
		return onTile;
	}

	public void setOnTile(Entity onTile) {
		this.onTile = onTile;
	}

}
