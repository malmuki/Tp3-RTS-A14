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
		if (trainer.getNextInQueue() != null) {

			if (timeElapsed >= trainer.getNextInQueue().time()) {
				if (trainer.spawnNext()) {
					return StateInteraction.ended;
				} else {
					return StateInteraction.blocked;
				}
			} else {
				return StateInteraction.notFinished;
			}
		}else {
			return StateInteraction.ended;
		}
	}

	public int getPourcentageDone() {
		return (int) (timeElapsed / trainer.getNextInQueue().time() * 100);
	}
}
