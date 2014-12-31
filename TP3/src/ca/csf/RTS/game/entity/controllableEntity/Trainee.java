package ca.csf.RTS.game.entity.controllableEntity;

import ca.csf.RTS.game.entity.controllableEntity.building.WatchTower;
import ca.csf.RTS.game.entity.controllableEntity.building.factory.*;
import ca.csf.RTS.game.entity.controllableEntity.human.*;

public enum Trainee {
	// wood cost, stone cost, time
	FOOTMAN(75, 0, 10f), WORKER(50, 0, 10f), BARRACK(150, 150, 10.0f), TOWN_CENTER(500, 500, 1.0F), WATCH_TOWER(150, 50, 2.0f), FORGE(200, 200, 30.0f), FOOTMAN_UPGRADE(
			200, 300, 100f), WORKER_UPGRADE(100, 100, 40f), TOWER_UPGRADE(300, 300, 50f);
	private int woodCost;
	private int stoneCost;
	private float time;

	private Trainee(int woodCost, int stoneCost, float time) {
		this.woodCost = woodCost;
		this.stoneCost = stoneCost;
		this.time = time;
	}

	public float time() {
		return time;
	}

	public int stoneCost() {
		return stoneCost;
	}

	public int woodCost() {
		return woodCost;
	}

	// for cost
	public static Trainee getTrainee(ControlableEntity entity) {
		switch (entity.getName()) {
		case FootMan.NAME:
			return FOOTMAN;
		case Worker.NAME:
			return WORKER;
		case Barrack.NAME:
			return BARRACK;
		case TownCenter.NAME:
			return TOWN_CENTER;
		case WatchTower.NAME:
			return WATCH_TOWER;
		case Forge.NAME:
			return FORGE;
		default:
			return null;
		}
	}
}
