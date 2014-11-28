package ca.csf.RTS.game.entity.ressource;

import java.util.ArrayList;

import ca.csf.RTS.game.entity.GameEntity;
import ca.csf.RTS.game.model.Tile;

public abstract class Ressource extends GameEntity{

	protected final int ressourceMax;
	protected int ressourceLeft;
	
	public Ressource(ArrayList<Tile> tiles,String name, int ressourceMax) {
		super(tiles, name);
		this.ressourceMax = ressourceMax;
		this.ressourceLeft = ressourceMax;
	}
}
