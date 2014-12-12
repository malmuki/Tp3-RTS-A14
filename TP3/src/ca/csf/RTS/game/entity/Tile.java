package ca.csf.RTS.game.entity;

import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import ca.csf.RTS.eventHandler.GameEventHandler;

public class Tile extends GameObject {

	public static final float TILE_SIZE = 20;

	private static final String NAME = "Tile";

	private final Vector2i mapLocation;
	private final Vector2f screenLocation;
	private Entity onTile;

	public Tile(Vector2i mapLocation, GameEventHandler game) {
		super(game);
		setOnTile(null);
		this.mapLocation = mapLocation;
		this.screenLocation = new Vector2f(mapLocation.x * TILE_SIZE, mapLocation.y * TILE_SIZE);
	}
	
	public int getDistance(Tile tile) {
	  int x;
	  int y;
	  x = mapLocation.x - tile.getMapLocation().x;
	  x *= 10;
	  x *= x;
	  y = mapLocation.y - tile.getMapLocation().y;
	  y *= y;
	  y *= y;
	  return (int)Math.sqrt(x + y);
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
		}else {
			return onTile.isObstacle();
		}
	}

	@Override
	public String getName() {
		return NAME;
	}
}
