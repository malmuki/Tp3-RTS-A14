package ca.csf.RTS.game.entity.state;

import ca.csf.RTS.game.entity.controllableEntity.ControlableEntity;
import ca.csf.RTS.game.entity.controllableEntity.Fightable;
import ca.csf.RTS.game.entity.controllableEntity.Fighter;

public class Attack implements State {

	private Fightable target;
	private Fighter source;

	public Attack(Fightable target, Fighter source) {
		this.target = target;
		this.source = source;
	}

	@Override
	public StateInteraction action(float deltaTime) {
		if (((ControlableEntity) source).getTilesOrigin().getDistance(((ControlableEntity) target).getTilesOrigin()) <= source.getRange()) { //TODO:fix the attack range calculation for buildings
			source.attack(target);
			if (target.getHP() <= 0) {
				return StateInteraction.ended;
			}else {
				return StateInteraction.notFinished;
			}
		}else {
			return StateInteraction.targetTooFar;
		}
	}

	public Fightable getTarget() {
		return target;
	}

}