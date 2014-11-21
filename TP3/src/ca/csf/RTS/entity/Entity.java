package ca.csf.RTS.entity;

import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;

public abstract class Entity {
	protected Sprite sprite;
	
	public abstract void draw(RenderTarget target);
}
