package ca.csf.RTS.game.entity.controllableEntity.building.factory;

import java.util.ArrayList;

import org.jsfml.system.Vector2i;

import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.Team;
import ca.csf.RTS.game.audio.SoundPlayer;
import ca.csf.RTS.game.entity.Entity;
import ca.csf.RTS.game.entity.GameObject;
import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.controllableEntity.Trainee;
import ca.csf.RTS.game.entity.controllableEntity.building.Building;
import ca.csf.RTS.game.entity.state.Training;

public abstract class Factory extends Building {
	public static final int MAX_QUEUE = 5;

	protected GameObject rallyPoint;
	protected ArrayList<Trainee> trainingQueue;

	public Factory(Tile originTile, Team team, GameEventHandler game, Vector2i dimension, int healthMax) {
		super(originTile, team, game, dimension, healthMax);

		rallyPoint = null;
		trainingQueue = new ArrayList<Trainee>();
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
				} else {
					stateStack.push(new Training(this));
				}
				break;

			case dead:
				for (Trainee trainee : trainingQueue) {
					team.addStone(trainee.stoneCost());
					team.addWood(trainee.woodCost());
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

	public ArrayList<Trainee> getQueue() {
		return trainingQueue;
	}

	public void order(Entity onTile) {
		rallyPoint = onTile.getTilesOrigin();
	}

	public void order(Tile target) {
		rallyPoint = target;
	}

	public Trainee getNextInQueue() {
		return trainingQueue.get(0);
	}

	public void addToQueue(int index) {
		if (trainingQueue.size() < MAX_QUEUE) {
			if (getTrainable(index).woodCost() <= team.getWood()) {
				if (getTrainable(index).stoneCost() <= team.getStoned()) {
					team.substractStone(getTrainable(index).stoneCost());
					team.substractWood(getTrainable(index).woodCost());
					if (trainingQueue.isEmpty()) {
						stateStack.push(new Training(this));
					}
					trainingQueue.add(getTrainable(index));
				} else {
					SoundPlayer.playSound(2);
				}
			} else {
				SoundPlayer.playSound(3);
			}
		} else {
			SoundPlayer.playSound(0);
		}
	}

	public abstract boolean spawnNext();

	protected abstract Trainee getTrainable(int index);
}
