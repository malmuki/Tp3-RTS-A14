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

	static {
		try {
			texture.loadFromFile(Paths.get("nature.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Soldat() {
		 sprite = new Sprite(texture);
	}
	
	public void draw(RenderTarget target){
		sprite.draw(target, RenderStates.DEFAULT);
	}
}
