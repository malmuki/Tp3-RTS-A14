package ca.csf.RTS.game.entity.state;

import ca.csf.RTS.game.entity.Entity;
import ca.csf.RTS.game.entity.controllableEntity.ControlableEntity;
import ca.csf.RTS.game.entity.controllableEntity.Fightable;
import ca.csf.RTS.game.entity.controllableEntity.Fighter;

public class Attack implements State {

	private Fighter source;
	private float attackProgression = 0f;
	
	public Attack(Fighter source) {
		this.source = source;
	}

	@Override
	public StateInteraction action(float deltaTime) {


		if (source.getTarget() != null && ((ControlableEntity) source.getTarget()).getHP() <= 0) {
			((ControlableEntity) source).setTarget(null);
			return StateInteraction.ended;
		}

		if (((ControlableEntity) source).getTilesOrigin().getDistance(((Entity) ((ControlableEntity) source).getTarget()).getTilesOrigin()) <= source.getRange()) {
			//TODO: calculation for buildings range
			attackProgression += deltaTime;
			
			if (attackProgression >= 0.5f) {
				source.attack((Fightable) source.getTarget());
				attackProgression = 0f;
			}
			
			return StateInteraction.notFinished;			
		} else {
			
			return StateInteraction.targetTooFar;
		}
	}

}
