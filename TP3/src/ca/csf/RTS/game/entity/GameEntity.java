package ca.csf.RTS.game.entity;

import java.util.ArrayList;

import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import ca.csf.RTS.game.model.Game;
import ca.csf.RTS.game.model.Tile;

public abstract class GameEntity {

	protected static Texture texture;
	protected Sprite sprite;
	protected ArrayList<Tile> tile;
	protected boolean selected;
	protected final String name;

	public GameEntity(ArrayList<Tile> tiles, String name) {
		selected = false;
		this.tile = tiles;
		this.name = name;
		sprite = new Sprite();

		Vector2i mostTopLeftTile = new Vector2i(Game.MAP_SIZE, Game.MAP_SIZE);
		for (Tile tile : tiles) {
			if (tile.getMapLocation().x < mostTopLeftTile.x
					&& tile.getMapLocation().y < mostTopLeftTile.y) {
				mostTopLeftTile = tile.getMapLocation();
			}
		}
		sprite.setPosition(new Vector2f(mostTopLeftTile.x * Tile.TILE_SIZE
				+ Tile.TILE_SIZE / 2, mostTopLeftTile.y * Tile.TILE_SIZE
				+ Tile.TILE_SIZE / 2));
	}

	public void draw(RenderTarget target) {
		sprite.draw(target, RenderStates.DEFAULT);
	}

	public void select() {
		selected = true;
	}

	public void deSelect() {
		selected = false;
	}
}
