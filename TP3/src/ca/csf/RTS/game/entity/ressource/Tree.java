package ca.csf.RTS.game.entity.ressource;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.jsfml.graphics.Texture;

import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.entity.Tile;

public class Tree extends Ressource {
	
	static {
		try {
			texture = new Texture();
			texture.loadFromFile(Paths.get("./ressource/Tree_Sprite.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static final String NAME = "Tree";
	private static final int RESSOURCE_MAX = 1500;
	
	public Tree(ArrayList<Tile> tiles, GameEventHandler game) {
		super( tiles, RESSOURCE_MAX, game);
	}
	@Override
	public String getName() {
		return NAME;
	}
}
