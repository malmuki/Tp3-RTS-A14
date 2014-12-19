package ca.csf.RTS.game.entity.state;

import ca.csf.RTS.game.entity.controllableEntity.Trainee;
import ca.csf.RTS.game.entity.controllableEntity.building.Foundation;
import ca.csf.RTS.game.entity.controllableEntity.human.Worker;
import ca.csf.RTS.game.pathFinding.DirectionFinder;

public class Building implements State {

	private Worker worker;
	private float buildProgression;
	private Trainee target;

	public Building(Worker worker, Trainee target) {
		this.worker = worker;
		this.target = target;
	}

	@Override
	public StateInteraction action(float deltaTime) {
		if (worker.getTilesOrigin().getDistance(DirectionFinder.getClosestLocation(worker)) <= 1) {
			buildProgression += deltaTime;

			if (buildProgression >= target.time()) {
				((Foundation) worker.getTarget()).transform();
				worker.setTarget(null);
				return StateInteraction.ended;
			}

			return StateInteraction.notFinished;
		} else {
			return StateInteraction.targetTooFar;
		}
	}
}
