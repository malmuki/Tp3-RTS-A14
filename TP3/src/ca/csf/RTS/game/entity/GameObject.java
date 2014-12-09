package ca.csf.RTS.game.entity;

import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

public abstract class GameObject {

	protected static Texture texture;
	protected Sprite sprite;
	protected final String name;

	public GameObject(String name) {
		this.name = name;
		sprite = new Sprite();
	}

	protected abstract void setSpritePos();

	public void draw(RenderTarget target) {
		sprite.setOrigin(0, 0);
		sprite.draw(target, RenderStates.DEFAULT);
	}

	public String getName() {
		return name;
	}
}