package ca.csf.RTS.entity.concrete;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

import ca.csf.RTS.entity.Entity;

public class Soldat extends Entity {

	private static Texture texture;
	private static final String PATH = "./ressource/allo.jpg";
	
	static {
		try {
			texture = new Texture();
			texture.loadFromFile(Paths.get(PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Soldat() {
		sprite = new Sprite(texture);
		sprite.setPosition(0, 0);
	}

	public void draw(RenderTarget target) {
		sprite.draw(target, RenderStates.DEFAULT);
	}
}
