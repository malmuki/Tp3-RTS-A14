package ca.csf.RTS.game.entity.ressource;

import java.util.ArrayList;

import ca.csf.RTS.game.entity.Entity;
import ca.csf.RTS.game.entity.Team;
import ca.csf.RTS.game.entity.Tile;

public abstract class Ressource extends Entity{
	
	private static final Team TEAM = Team.NATURE;

	protected final int ressourceMax;
	protected int ressourceLeft;
	
	public Ressource(ArrayList<Tile> tiles,String name, int ressourceMax) {
		super(tiles, name, TEAM);
		this.ressourceMax = ressourceMax;
		this.ressourceLeft = ressourceMax;
	}
	
	public void order(Entity onTile) {}

    public void order(Tile target) {}
    
    public void doTasks() {}
}