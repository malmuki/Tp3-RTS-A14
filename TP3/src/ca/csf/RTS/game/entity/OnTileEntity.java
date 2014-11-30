package ca.csf.RTS.game.entity;

import java.util.ArrayList;

import org.jsfml.system.Vector2i;

import ca.csf.RTS.game.Game;

public abstract class OnTileEntity extends GameEntity {

	protected ArrayList<Tile> tile;

	public OnTileEntity(ArrayList<Tile> tiles, String name) {
		super(name);
		this.tile = tiles;
		setSpritePos();
	}

	@Override
	protected void setSpritePos() {
		Vector2i mostTopLeftTile = new Vector2i(Game.MAP_SIZE, Game.MAP_SIZE);
		for (Tile tile : tile) {
			if (tile.getMapLocation().x < mostTopLeftTile.x && tile.getMapLocation().y < mostTopLeftTile.y) {
				mostTopLeftTile = tile.getMapLocation();
			}
		}
		sprite.setPosition(mostTopLeftTile.x*Tile.TILE_SIZE,mostTopLeftTile.y*Tile.TILE_SIZE);
	}
}
