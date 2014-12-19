package ca.csf.RTS.game.entity.controllableEntity.building.factory;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2i;

import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.Team;
import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.controllableEntity.human.Worker;
import ca.csf.RTS.game.entity.state.Idle;
import ca.csf.RTS.game.entity.state.State;
import ca.csf.RTS.game.pathFinding.PathFinder;

public class TownCenter extends Factory {

	private static String TEXTURE_PATH = "./ressource/towncenter.png";
	private static Texture texture;
	private static final String NAME = "Town Center";
	public static final Vector2i DIMENSION = new Vector2i(8, 8);

	public TownCenter(Tile originTile, Team team, GameEventHandler game) {
		super(originTile, team, game, DIMENSION, team.getTownCenterModel().getHealthMax());
		try {
			if (texture == null) {
				texture = new Texture();
				texture.loadFromFile(Paths.get(TEXTURE_PATH));
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

	protected State getDefaultState() {
		return new Idle();
	}

	@Override
	protected Trainee getTrainable(int index) {
		switch (index) {
		case 0:
			return Trainee.WORKER;
		default:
			return null;
		}
	}

	@Override
	public boolean spawnNext() {
		Tile tile = PathFinder.findSpawningSpot(this);
		if (tile != null) {
			switch (trainingQueue.get(0)) {
			case WORKER:
				game.add(new Worker(tile, team, game));
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
		return Vector2i.add(tilesOrigin.getMapLocation(), new Vector2i(4, 4));
	}
}
