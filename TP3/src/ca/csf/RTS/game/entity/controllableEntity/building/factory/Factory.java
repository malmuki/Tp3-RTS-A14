package ca.csf.RTS.game.entity.controllableEntity.building.factory;

import java.util.ArrayList;

import org.jsfml.system.Vector2i;

import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.Team;
import ca.csf.RTS.game.entity.Entity;
import ca.csf.RTS.game.entity.GameObject;
import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.controllableEntity.building.Building;
import ca.csf.RTS.game.entity.state.State;
import ca.csf.RTS.game.entity.state.Training;

public abstract class Factory extends Building {
	public static final int MAX_QUEUE = 5;

	protected GameObject rallyPoint;
	protected ArrayList<Trainable> trainingQueue;

	public Factory(Tile originTile, int maxHealth, Team team, GameEventHandler game, Vector2i dimension) {
		super(originTile, maxHealth, team, game, dimension);
		rallyPoint = null;
		trainingQueue = new ArrayList<Trainable>();
	}

	@Override
	public void doTasks(float deltaTime) {
		if (!stateStack.isEmpty()) {
			switch (stateStack.peek().action(deltaTime)) {

			case notFinished:
				break;

			case ended:
				trainingQueue.remove(0);
				stateStack.pop();
				if (trainingQueue.isEmpty()) {
					stateStack.push(getDefaultState());
				}else {
					stateStack.push(new Training(this));
				}
				break;

			case dead:
				for (Trainable trainable : trainingQueue) {
					team.addStone(trainable.stoneCost);
					team.addWood(trainable.woodCost);
				}
				game.remove(this);
				break;

			default:
				break;
			}
		} else {
			stateStack.push(getDefaultState());
		}
	}

	public void order(Entity onTile) {
		rallyPoint = onTile.getTilesOrigin();
	}

	public void order(Tile target) {
		rallyPoint = target;
	}
	
	public Trainable getNextInQueue(){
		return trainingQueue.get(0);
	}

	public void addToQueue(int index) {
		if (trainingQueue.size() < MAX_QUEUE) {
			if (getTrainable(index).woodCost <= team.getWood()) {
				if (getTrainable(index).stoneCost <= team.getStoned()) {
					team.substractStone(getTrainable(index).stoneCost);
					team.substractWood(getTrainable(index).woodCost);
					trainingQueue.add(getTrainable(index));
					if (trainingQueue.isEmpty()) {
						stateStack.pop();
						stateStack.push(new Training(this));
					}
				} else {
					// spam un son de pas assez de stone!!!!
				}
			} else {
				// spam un son de pas assez de wood!!!!
			}
		} else {
			// spam un son de pas assez de place dans la queue!!!!
		}
	}

	public abstract boolean spawnNext();

	protected abstract State getDefaultState();

	protected abstract Trainable getTrainable(int index);
}