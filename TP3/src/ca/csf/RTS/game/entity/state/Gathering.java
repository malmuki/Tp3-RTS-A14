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

		if (worker.getTilesOrigin().getDistance(worker.getTarget().getTilesOrigin()) < 14) {
			switch (worker.getTarget().getName()) {
			// 1/sec
			case "Stone":
					worker.getTeam().addStone(((Ressource) worker.getTarget()).removeRessources(1));
				break;
			case "Tree":
					worker.getTeam().addWood(((Ressource) worker.getTarget()).removeRessources(1));
				break;

			default:
				break;
			}

		}

		return StateInteraction.ressourceDepleted;
	}
}
