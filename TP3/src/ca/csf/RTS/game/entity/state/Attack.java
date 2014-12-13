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
<<<<<<< HEAD
		if (((ControlableEntity) source)
				.getCurrentTiles()
				.get(0)
				.getDistance(
						((Entity) ((ControlableEntity) source).getTarget()).getCurrentTiles().get(0)) <= source.getRange()) {
								
			source.attack((Fightable) ((ControlableEntity) source).getTarget());
			
			if (((ControlableEntity) ((ControlableEntity) source).getTarget()).getHP() <= 0) {
				((ControlableEntity) source).setTarget(null);
=======
		if (((ControlableEntity) source).getTilesOrigin().getDistance(((ControlableEntity) target).getTilesOrigin()) <= source.getRange()) { //TODO:fix the attack range calculation for buildings
			source.attack(target);
			if (target.getHP() <= 0) {
>>>>>>> refs/remotes/origin/dev
				return StateInteraction.ended;
			} else {
				return StateInteraction.notFinished;
			}
		} else {
			return StateInteraction.targetTooFar;
		}
	}

}
