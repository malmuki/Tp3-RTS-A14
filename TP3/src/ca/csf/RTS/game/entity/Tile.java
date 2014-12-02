package ca.csf.RTS.game.entity;

import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

public class Tile extends GameEntity {

	public static final float TILE_SIZE = 20;

	private static final String NAME = "Tile";

	private final Vector2i mapLocation;
	private final Vector2f screenLocation;
	private OnTileEntity onTile;

	public Tile(Vector2i mapLocation) {
		super(NAME);
		setOnTile(null);
		this.mapLocation = mapLocation;
		this.screenLocation = new Vector2f(mapLocation.x * TILE_SIZE + TILE_SIZE / 2, mapLocation.y * TILE_SIZE + TILE_SIZE / 2);
	}

	public Vector2f getScreenLocation() {
		return screenLocation;
	}

	public Vector2i getMapLocation() {
		return mapLocation;
	}

	public OnTileEntity getOnTile() {
		return onTile;
	}

	public void setOnTile(OnTileEntity onTile) {
		this.onTile = onTile;
	}

	@Override
	protected void setSpritePos() {
		sprite.setPosition(screenLocation);	
	}

}
