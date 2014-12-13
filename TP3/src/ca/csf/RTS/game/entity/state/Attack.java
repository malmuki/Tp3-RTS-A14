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
		if (((ControlableEntity) source)
				.getCurrentTiles()
				.get(0)
				.getDistance(
						((Entity) ((ControlableEntity) source).getTarget()).getCurrentTiles().get(0)) <= source.getRange()) {
								
			source.attack((Fightable) ((ControlableEntity) source).getTarget());
			
			if (((ControlableEntity) ((ControlableEntity) source).getTarget()).getHP() <= 0) {
				((ControlableEntity) source).setTarget(null);
				return StateInteraction.ended;
			} else {
				return StateInteraction.notFinished;
			}
		} else {
			return StateInteraction.targetTooFar;
		}
	}

}
