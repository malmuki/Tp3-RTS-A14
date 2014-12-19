package ca.csf.RTS.game.entity.controllableEntity.building.factory;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2i;

import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.Team;
import ca.csf.RTS.game.audio.SoundPlayer;
import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.controllableEntity.Trainee;
import ca.csf.RTS.game.entity.controllableEntity.human.FootMan;
import ca.csf.RTS.game.entity.state.Idle;
import ca.csf.RTS.game.entity.state.State;
import ca.csf.RTS.game.pathFinding.PathFinder;

public class Barrack extends Factory {

	private static final String TEXTURE_PATH = "./ressource/buildings.png";
	private static Texture texture;
	public static final String NAME = "Barrack";
	public static final Vector2i DIMENSION = new Vector2i(6, 6);

	public Barrack(Tile originTile, Team team, GameEventHandler game) {
		super(originTile, team, game, DIMENSION, team.getBarrackModel().getHealthMax());
		try {
			if (texture == null) {
				texture = new Texture();
				texture.loadFromFile(Paths.get(TEXTURE_PATH));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		sprite.setTexture(texture);
		sprite.setTextureRect(new IntRect(303, 457, 100, 100));
		setSpritePos();
	}

	@Override
	public String getName() {
		return NAME;
	}

	public State getDefaultState() {
		return new Idle();
	}

	@Override
	protected Trainee getTrainable(int index) {
		switch (index) {
		case 0:
			return Trainee.FOOTMAN;
		default:
			return null;
		}
	}

	@Override
	public boolean spawnNext() {
		Tile tile = PathFinder.findSpawningSpot(this);
		if (tile != null) {
			switch (trainingQueue.get(0)) {
			case FOOTMAN:
				game.add(new FootMan(tile, team, game));
				SoundPlayer.playSound(1, 0);
				break;
			default:
				break;
			}

			return true;
		} else {
			return false;
		}
	}

	@Override
	public Vector2i getCenter() {
		return Vector2i.add(tilesOrigin.getMapLocation(), new Vector2i(3, 3));
	}
}
