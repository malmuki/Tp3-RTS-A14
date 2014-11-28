package ca.csf.RTS.game.entity.controllableEntity.human;

import java.util.ArrayList;
import ca.csf.RTS.game.entity.controllableEntity.Fightable;
import ca.csf.RTS.game.entity.controllableEntity.Fighter;
import ca.csf.RTS.game.model.Tile;

public class FootMan extends Humans implements Fighter{
	
	private static final int MAX_HEALTH = 100;
	private static final String NAME = "Footman";
	
	public FootMan(ArrayList<Tile> tiles) {
		super(tiles, NAME , MAX_HEALTH);
	}

	@Override
	public void attack(Fightable target) {
		
	}
}
