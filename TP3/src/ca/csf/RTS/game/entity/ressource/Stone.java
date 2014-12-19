package ca.csf.RTS.game.entity.ressource;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2i;

import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.Team;
import ca.csf.RTS.game.entity.Tile;

public class Stone extends Ressource {

	private static Texture texture;
	private static final String NAME = "Stone";
	private static final int RESSOURCE_MAX = 400;
	private static final Vector2i SIZE = new Vector2i(2, 2);

	public Stone(Tile originTile, Team team, GameEventHandler game) {
		super(originTile, RESSOURCE_MAX, game, team);
		try {
			if (texture == null) {
				texture = new Texture();
				texture.loadFromFile(Paths.get("./ressource/rock.png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		dimensions = SIZE;
		sprite.setTexture(texture);
		sprite.scale(0.17f, 0.24f);
		setSpritePos();
	}

	@Override
	public String getName() {
		return NAME;
	}
}
