package ca.csf.RTS.game.entity;

import java.util.ArrayList;

import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;

import ca.csf.RTS.game.model.Tile;

public abstract class GameEntity{
	
	protected Sprite sprite;
	protected ArrayList<Tile> tile;
	protected boolean selected;
	protected final String name;
	
	public GameEntity(ArrayList<Tile> tiles, String name) {
		this.tile = tiles;
		this.name = name;
	}
	
	public void draw(RenderTarget target) {
		sprite.draw(target, RenderStates.DEFAULT);
	}
	
}
