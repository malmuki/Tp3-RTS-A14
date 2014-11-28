package ca.csf.RTS.game.entity.controllableEntity.human;

import java.util.ArrayList;

import ca.csf.RTS.game.model.Tile;

public class Worker extends Humans {
	private static final int MAX_HEALTH = 100;
	private static final String NAME = "Worker";

	public Worker(ArrayList<Tile> tiles) {
		super(tiles, NAME, MAX_HEALTH);
	}
}
