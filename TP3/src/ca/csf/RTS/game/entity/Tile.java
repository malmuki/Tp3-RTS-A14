package ca.csf.RTS.game.entity;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import ca.csf.RTS.eventHandler.GameEventHandler;

public class Tile extends GameObject {

	public static final float TILE_SIZE = 32;

	private static final String NAME = "Tile";
	private static final String TEXTURE_PATH = "./ressource/footman.png";

	private static Texture texture;
	private final Vector2i mapLocation;
	private final Vector2f screenLocation;
	private Entity onTile;

	public Tile(Vector2i mapLocation, GameEventHandler game) {
		super(game);
		try {
			if (texture == null) {
				texture = new Texture();
				texture.loadFromFile(Paths.get(TEXTURE_PATH));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		setOnTile(null);
		this.mapLocation = mapLocation;
		this.screenLocation = new Vector2f(mapLocation.x * TILE_SIZE, mapLocation.y * TILE_SIZE);
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

	@Override
	protected void setSpritePos() {
		sprite.setPosition(screenLocation);
	}

	public boolean hasObstacle() {
		if (onTile == null) {
			return false;
		} else {
			return onTile.isObstacle();
		}
	}

	@Override
	public String getName() {
		return NAME;
	}

	public int getDistance(Tile tile) {
		int x = mapLocation.x - tile.getMapLocation().x;
		x *= x;
		int y = mapLocation.y - tile.getMapLocation().y;
		y *= y;
		return (int) Math.sqrt(x + y);
	}

	public int getDistance(Vector2i location) {
		int x = mapLocation.x - location.x;
		x *= x;
		int y = mapLocation.y - location.y;
		y *= y;
		return (int) Math.sqrt(x + y);
	}
}
