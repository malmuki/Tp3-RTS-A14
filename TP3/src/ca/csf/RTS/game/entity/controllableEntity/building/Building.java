package ca.csf.RTS.game.entity.controllableEntity.building;

import java.util.ArrayList;

import ca.csf.RTS.game.entity.Team;
import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.controllableEntity.ControlableEntity;

public abstract class Building extends ControlableEntity {
	public Building(ArrayList<Tile> tiles, String name, int maxHealth, Team team) {
		super(tiles, name, maxHealth, team);
	}
}