package ca.csf.RTS.game.entity.ressource;

import java.util.ArrayList;

import ca.csf.RTS.game.entity.GameEntity;
import ca.csf.RTS.game.model.Tile;

public abstract class Ressource extends GameEntity{

	public Ressource(ArrayList<Tile> tiles,String name) {
		super( tiles, name);
	}
}
