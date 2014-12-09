package ca.csf.RTS.game.entity.controllableEntity.building.humanFactory;

import java.util.ArrayList;

import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.entity.Team;
import ca.csf.RTS.game.entity.Tile;

public class Barrack extends HumanFactory {
	
	private static final int MAX_HEALTH = 1000;
	private static final String NAME = "Barrack";

	public Barrack(ArrayList<Tile> tiles , Team team, GameEventHandler game) {
		super(tiles, NAME, MAX_HEALTH , team, game);
	}

	@Override
	public void doTasks() {
		
	}
}