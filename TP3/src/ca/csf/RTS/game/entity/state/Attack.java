package ca.csf.RTS.game.entity.state;

import ca.csf.RTS.game.entity.Entity;
import ca.csf.RTS.game.entity.controllableEntity.ControlableEntity;
import ca.csf.RTS.game.entity.controllableEntity.Fightable;
import ca.csf.RTS.game.entity.controllableEntity.Fighter;

public class Attack implements State {

	private Fighter source;

	public Attack(Fighter source) {
		this.source = source;
	}

	@Override
	public StateInteraction action(float deltaTime) {

		if (((ControlableEntity) ((ControlableEntity) source).getTarget()).getHP() <= 0) {
			((ControlableEntity) source).setTarget(null);
			return StateInteraction.ended;
		}

		if (((ControlableEntity) source).getTilesOrigin().getDistance(
				((Entity) ((ControlableEntity) source).getTarget()).getTilesOrigin()) <= source.getRange()) { // TODO:fix
																												// the
																												// attack
			source.attack((Fightable) source.getTarget()); // calculation
															// for
															// buildings //
															// range

			if (((ControlableEntity) source.getTarget()).getHP() <= 0) {
				return StateInteraction.ended;
			} else {
				return StateInteraction.notFinished;
			}
		} else {
			return StateInteraction.targetTooFar;
		}
	}

}
