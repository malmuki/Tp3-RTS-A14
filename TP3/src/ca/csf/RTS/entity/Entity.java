package ca.csf.RTS.entity;

import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;

import ca.csf.RTS.game.model.Tile;

public abstract class Entity {
	protected Sprite sprite;
	protected Tile tile;
	
	public abstract void draw(RenderTarget target);
}
