package ca.csf.RTS.game.entity.ressource;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.Texture;

import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.Team;
import ca.csf.RTS.game.entity.Tile;

public class Stone extends Ressource {
	
	private static Texture texture;
	private static final String NAME = "Stone";
	private static final int RESSOURCE_MAX = 1000;
	
	public Stone(Tile originTile, GameEventHandler game, Team team) {
		super(originTile, RESSOURCE_MAX, game, team);
		try {
			if (texture == null) {
				texture = new Texture();
				texture.loadFromFile(Paths.get("./ressource/stone.png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		sprite.setTexture(texture);
		setSpritePos();
	}

	@Override
	public String getName() {
		return NAME;
	}
}
