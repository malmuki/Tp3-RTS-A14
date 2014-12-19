package ca.csf.RTS.game.entity.state;

import ca.csf.RTS.game.entity.controllableEntity.human.Worker;
import ca.csf.RTS.game.entity.ressource.Ressource;
import ca.csf.RTS.game.pathFinding.DirectionFinder;

public class Gathering implements State {

	private static final float MIN_COLLECT_TIME = 0.5f;
	private static final int RESSOURCES_PER_COLLECT_TIME = 2;

	private Worker worker;
	private float timeElapse = 0f;

	public Gathering(Worker worker) {
		this.worker = worker;
	}

	@Override
	public StateInteraction action(float deltaTime) {

		if (worker.getTarget() != null) {
			if (worker.getTilesOrigin().getDistance(DirectionFinder.getClosestLocation(worker)) <= 1) {
				timeElapse += deltaTime;
				if (timeElapse >= MIN_COLLECT_TIME) {
					timeElapse = 0.0f;
					switch (worker.getTarget().getName()) {
					case "Stone":
						worker.getTeam().addStone(((Ressource) worker.getTarget()).removeRessources(RESSOURCES_PER_COLLECT_TIME));
						if (((Ressource) worker.getTarget()).getRessources() <= 0) {
							if (worker.getTarget() == worker.getTarget().getTilesOrigin().getOnTile()) {
								worker.getGame().remove(worker.getTarget());
							}
							worker.setTarget(null);
							return StateInteraction.ressourceDepleted;
						}
						break;
					case "Tree":
						worker.getTeam().addWood(((Ressource) worker.getTarget()).removeRessources(RESSOURCES_PER_COLLECT_TIME));
						if (((Ressource) worker.getTarget()).getRessources() <= 0) {
							if (worker.getTarget() == worker.getTarget().getTilesOrigin().getOnTile()) {
								worker.getGame().remove(worker.getTarget());
							}

							worker.setTarget(null);
							return StateInteraction.ressourceDepleted;
						}
					default:
						break;
					}
					return StateInteraction.notFinished;
				} else {
					return StateInteraction.notFinished;
				}
			} else {
				return StateInteraction.targetTooFar;
			}
		} else {
			return StateInteraction.ended;
		}
	}

	@Override
	public String getStateName() {
		return "Gathering";
	}
}
