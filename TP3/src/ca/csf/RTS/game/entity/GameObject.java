package ca.csf.RTS.game.entity;

import org.jsfml.graphics.Sprite;

import ca.csf.RTS.eventHandler.GameEventHandler;

public abstract class GameObject {

	protected Sprite sprite;
	protected GameEventHandler game;

	public GameObject(GameEventHandler game) {
		this.game = game;
		sprite = new Sprite();
	}

	protected abstract void setSpritePos();

	public abstract String getName();

	public GameEventHandler getGame() {
		return game;
	}
}