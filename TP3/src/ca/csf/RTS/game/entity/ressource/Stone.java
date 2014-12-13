package ca.csf.RTS.game.entity.ressource;

import java.util.ArrayList;

import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.Team;
import ca.csf.RTS.game.entity.Tile;

public class Stone extends Ressource {
	
	private static final String NAME = "Stone";
	private static final int RESSOURCE_MAX = 1500;
	
	public Stone(Tile originTile, GameEventHandler game, Team team) {
		super(originTile, RESSOURCE_MAX, game, team);
	}

	@Override
	public String getName() {
		return NAME;
	}
}
