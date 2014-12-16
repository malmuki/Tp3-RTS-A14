package ca.csf.RTS.game.entity.state;

import ca.csf.RTS.game.entity.controllableEntity.ControlableEntity;
import ca.csf.RTS.game.entity.controllableEntity.Fighter;

public class Attack implements State {

	private Fighter source;
	private float attackProgression = 0.0f;

	public Attack(Fighter source) {
		this.source = source;
	}

	@Override
	public StateInteraction action(float deltaTime) {

		if (source.getTarget() != null) {
			if (((ControlableEntity) source.getTarget()).getHP() <= 0) {
				((ControlableEntity) source).setTarget(null);
				return StateInteraction.ended;
			}

			if (((ControlableEntity) source).getTilesOrigin().getDistance(((ControlableEntity) source).getTarget().getTilesOrigin()) <= source.getRange()) {
				// TODO: calculation for buildings range
				attackProgression += deltaTime;

				if (attackProgression > 0.5f) {
					source.attack();
					attackProgression = 0.0f;
				}

				return StateInteraction.notFinished;
			} else {

				return StateInteraction.targetTooFar;
			}
		}
		return StateInteraction.ended;
	}
}