package ca.csf.RTS.game.entity.controllableEntity.human;

import java.util.ArrayList;

import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.state.Idle;
import ca.csf.RTS.game.entity.state.State;

public class Worker extends Human {
	private static final int MAX_HEALTH = 100;
	private static final String NAME = "Worker";

	public Worker(ArrayList<Tile> tiles) {
		super(tiles, NAME, MAX_HEALTH);
	}

  @Override
  public State getDefaultState() {
    return new Idle(this);
  }
}
