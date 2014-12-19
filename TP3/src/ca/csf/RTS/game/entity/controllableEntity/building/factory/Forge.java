package ca.csf.RTS.game.entity.controllableEntity.building.factory;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2i;

import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.Team;
import ca.csf.RTS.game.entity.Entity;
import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.controllableEntity.Trainee;
import ca.csf.RTS.game.entity.state.State;
import ca.csf.RTS.upgrades.FootmanUpgrade;
import ca.csf.RTS.upgrades.WatchTowerUpgrade;
import ca.csf.RTS.upgrades.WorkerUpgrade;

public class Forge extends Factory {

	private static final String TEXTURE_PATH = "./ressource/buildings.png";
	private static Texture texture;
	public static final String NAME = "Forge";
	public static final Vector2i DIMENSION = new Vector2i(5, 7);

	public Forge(Tile originTile, Team team, GameEventHandler game) {
		super(originTile, team, game, DIMENSION, team.getForgeModel().getHealthMax());
		try {
			if (texture == null) {
				texture = new Texture();
				texture.loadFromFile(Paths.get(TEXTURE_PATH));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		sprite.setTexture(texture);
		sprite.setTextureRect(new IntRect(3, 263, 100, 100));
		setSpritePos();
	}

	@Override
	public void order(Entity onTile) {
	}

	@Override
	public void order(Tile target) {
	}

	@Override
	public void doTasks(float deltaTime) {

	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public boolean spawnNext() {
		switch (trainingQueue.get(0)) {
		case FOOTMAN_UPGRADE:
			FootmanUpgrade.applyUpgrade(this.getTeam());
			break;
		case WORKER_UPGRADE:
			WorkerUpgrade.applyUpgrade(this.getTeam());
			break;
		case TOWER_UPGRADE:
			WatchTowerUpgrade.applyUpgrade(this.getTeam());
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	protected State getDefaultState() {
		return null;
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
	public Vector2i getCenter() {
		return Vector2i.add(tilesOrigin.getMapLocation(), new Vector2i(3, 4));
	}
}
