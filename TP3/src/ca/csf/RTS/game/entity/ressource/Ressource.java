package ca.csf.RTS.game.entity.ressource;

import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.Team;
import ca.csf.RTS.game.entity.Entity;
import ca.csf.RTS.game.entity.Tile;

public abstract class Ressource extends Entity {

	protected final int ressourceMax;
	protected int ressourceLeft;

	public Ressource(Tile tile, int ressourceMax, GameEventHandler game, Team team) {
		super(tile, team, game);
		this.ressourceMax = ressourceMax;
		this.ressourceLeft = ressourceMax;
	}

	public void order(Entity onTile) {
	}

	public void order(Tile target) {
	}

	public void doTasks(float deltaTime) {
	}

	@Override
	public boolean isObstacle() {
		return true;
	}
	
	public int removeRessources(int quantity){
		if (ressourceLeft >= quantity) {
			ressourceLeft -= quantity;
			return quantity;
		}else {
			return ressourceLeft;
		}
	}
	public int getRessources(){
		return ressourceLeft;
	}
}
