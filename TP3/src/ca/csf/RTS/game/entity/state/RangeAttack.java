package ca.csf.RTS.game.entity.state;

import ca.csf.RTS.game.entity.Entity;
import ca.csf.RTS.game.entity.controllableEntity.ControlableEntity;
import ca.csf.RTS.game.entity.controllableEntity.Fighter;

public class RangeAttack implements State {

	private Fighter source;
	private float attackProgression = 0.0f;

	public RangeAttack(Fighter source) {
		this.source = source;
	}

	@Override
	public StateInteraction action(float deltaTime) {
		if (source.getTarget() != null) {
			if (((ControlableEntity) source.getTarget()).getHP() <= 0) {
				((ControlableEntity) source).setTarget(null);
				return StateInteraction.ended;
			}
			int distance = source.getTarget().getTilesOrigin().getMapLocation().x - ((Entity) source).getTilesOrigin().getMapLocation().x;
			if (distance < 0) {
				distance *= -1;
			}
			if (distance <= source.getRange()) {
				distance = source.getTarget().getTilesOrigin().getMapLocation().y - ((Entity) source).getTilesOrigin().getMapLocation().y;
				if (distance < 0) {
					distance *= -1;
				}
				if (distance <= source.getRange()) {

					attackProgression += deltaTime;

					if (attackProgression > 0.5f) {
						source.attack();
						attackProgression = 0.0f;
					}

					return StateInteraction.notFinished;
				} else {
					return StateInteraction.targetTooFar;
				}
			} else {

				return StateInteraction.targetTooFar;
			}
		}
		return StateInteraction.ended;
	}
}
