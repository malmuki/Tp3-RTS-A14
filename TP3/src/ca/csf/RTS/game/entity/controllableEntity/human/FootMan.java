package ca.csf.RTS.game.entity.controllableEntity.human;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.jsfml.graphics.Texture;

import ca.csf.RTS.game.entity.Entity;
import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.controllableEntity.Fightable;
import ca.csf.RTS.game.entity.controllableEntity.Fighter;
import ca.csf.RTS.game.entity.state.Alert;
import ca.csf.RTS.game.entity.state.State;

public class FootMan extends Human implements Fighter {

	static {
		try {
			texture = new Texture();
			texture.loadFromFile(Paths.get("./ressource/soldat.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static final int MAX_HEALTH = 100;
	private static final String NAME = "Footman";

	public FootMan(ArrayList<Tile> tiles) {
		super(tiles, NAME, MAX_HEALTH);
		sprite.setTexture(texture);
	}

	@Override
	public void attack(Fightable target) {

	}

	@Override
	public void order(Entity target) {

	}

	@Override
	public State getDefaultState() {
		return new Alert();
	}

	@Override
	public void doTasks() {

	}

}
