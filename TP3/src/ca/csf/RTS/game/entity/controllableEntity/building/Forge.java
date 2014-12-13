package ca.csf.RTS.game.entity.controllableEntity.building;

import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.Team;
import ca.csf.RTS.game.entity.Entity;
import ca.csf.RTS.game.entity.Tile;

public class Forge extends Building {

	private static final int MAX_HEALTH = 1000;
	private static final String NAME = "Forge";
	
	public Forge(Tile originTile, Team team, GameEventHandler game) {
		super(originTile, MAX_HEALTH, team, game);
	}
	
	public void order(Entity onTile) {}

    public void order(Tile target) {}

	@Override
	public void doTasks(float deltaTime) {
		
	}

	@Override
	public String getName() {
		return NAME;
	}
}
