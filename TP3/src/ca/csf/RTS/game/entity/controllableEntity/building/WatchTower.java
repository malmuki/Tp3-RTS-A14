package ca.csf.RTS.game.entity.controllableEntity.building;

import java.util.ArrayList;

import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.Team;
import ca.csf.RTS.game.entity.Entity;
import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.controllableEntity.Fightable;
import ca.csf.RTS.game.entity.controllableEntity.Fighter;
import ca.csf.RTS.game.entity.controllableEntity.Watcher;

public class WatchTower extends Building implements Fighter,Watcher {
	
	private static final int MAX_HEALTH = 1000;
	private static final String NAME = "WatchTower";
	private static final int RANGE = 112;
	private static final int DAMAGE = 10;

	public WatchTower(ArrayList<Tile> tiles, Team team, GameEventHandler game) {
		super(tiles, MAX_HEALTH, team, game);
	}

	@Override
	public void attack(Fightable target) {

	}

	public void order(Entity onTile) {

	}

	public void order(Tile target) {
	}

	@Override
	public void doTasks(float deltaTime) {

	}

	@Override
	public Entity search() {
		// TODO: dijkstra
		return null;
	}

	@Override
	public int getRange() {
		return RANGE;
	}

	@Override
	public int getDamage() {
		return DAMAGE;
	}

	@Override
	public String getName() {
		return NAME;
	}
}