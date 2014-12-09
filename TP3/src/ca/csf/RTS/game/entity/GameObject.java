package ca.csf.RTS.game.entity;

import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

import ca.csf.RTS.eventHandler.GameEventHandler;

public abstract class GameObject {

	protected static Texture texture;
	protected Sprite sprite;
	protected final String name;
	protected GameEventHandler game;

	public GameObject(String name, GameEventHandler game) {
		this.name = name;
		this.game = game;
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