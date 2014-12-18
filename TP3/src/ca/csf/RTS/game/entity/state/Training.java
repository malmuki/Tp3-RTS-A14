package ca.csf.RTS.game.entity.state;

import ca.csf.RTS.game.entity.controllableEntity.building.factory.Factory;

public class Training implements State {

	private Factory trainer;
	private float timeElapsed = 0f;

	public Training(Factory trainer) {
		this.trainer = trainer;
	}

	@Override
	public StateInteraction action(float deltaTime) {
		timeElapsed += deltaTime;
		if (timeElapsed < trainer.getNextInQueue().time()) {
			return StateInteraction.notFinished;
		} else {
			if (trainer.spawnNext()) {
				return StateInteraction.ended;
			} else {
				return StateInteraction.blocked;
			}
		}
	}
}
