package ca.csf.RTS.game.entity.state;

import ca.csf.RTS.game.entity.controllableEntity.human.Worker;
import ca.csf.RTS.game.entity.ressource.Ressource;

public class Gathering implements State {

	private Worker worker;

	public Gathering(Worker worker) {
		this.worker = worker;
	}

	@Override
	public StateInteraction action(float deltaTime) {

		if (worker.getTarget() != null) {

			if (worker.getTilesOrigin().getDistance(worker.getTarget().getTilesOrigin()) < 14) {

				switch (worker.getTarget().getName()) {
				case "Stone":
					worker.getTeam().addStone(((Ressource) worker.getTarget()).removeRessources(1));
					if (((Ressource) worker.getTarget()).getRessources() <= 0) {
						worker.getGame().remove(worker.getTarget());
						worker.setTarget(null);
						return StateInteraction.ressourceDepleted;
					}
					break;
				case "Tree":
					worker.getTeam().addWood(((Ressource) worker.getTarget()).removeRessources(1));
					if (((Ressource) worker.getTarget()).getRessources() <= 0) {
						worker.getGame().remove(worker.getTarget());
						worker.setTarget(null);
						return StateInteraction.ressourceDepleted;
					}
				default:
					break;
				}
				return StateInteraction.notFinished;
			} else {
				return StateInteraction.targetTooFar;
			}
		} else {
			return StateInteraction.ended;
		}
	}
}
