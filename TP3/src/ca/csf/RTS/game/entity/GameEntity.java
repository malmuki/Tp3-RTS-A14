package ca.csf.RTS.game.entity;

import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

public abstract class GameEntity {

	protected static Texture texture;
	protected Sprite sprite;

	protected boolean selected;
	protected final String name;

	public GameEntity(String name) {
		selected = false;
		this.name = name;
		sprite = new Sprite();
	}
	
	protected abstract void setSpritePos();

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
