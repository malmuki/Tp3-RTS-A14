package ca.csf.RTS.game.entity.controllableEntity.human;

import java.util.ArrayList;

import ca.csf.RTS.game.entity.controllableEntity.Fightable;
import ca.csf.RTS.game.entity.controllableEntity.Fighter;
import ca.csf.RTS.game.model.Tile;

public class FootMan extends Humans implements Fighter{
	public FootMan(ArrayList<Tile> tiles, String name) {
		super(tiles, name);
	}

	@Override
	public void attack(Fightable target) {
		
	}
}
