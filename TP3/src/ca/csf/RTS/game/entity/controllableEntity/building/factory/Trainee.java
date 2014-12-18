package ca.csf.RTS.game.entity.controllableEntity.building.factory;

public enum Trainee {
	// wood cost, stone cost, time
	FOOTMAN(50, 0, 15f), WORKER(50, 0, 10f), FOOTMAN_UPGRADE(100, 100, 1f), WORKER_UPGRADE(100, 100, 1f), TOWER_UPGRADE(100, 100, 1f);
	int woodCost;
	int stoneCost;
	float time;

	private Trainee(int woodCost, int stoneCost, float time) {
		this.woodCost = woodCost;
		this.stoneCost = stoneCost;
		this.time = time;
	}
	
	public float time(){
		return time;
	}
}