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
	public StateInteraction action() {
		if (((ControlableEntity) target)
				.getCurrentTiles()
				.get(0)
				.getDistance(
						((ControlableEntity) source).getCurrentTiles().get(0)) <= 14) {
			source.attack(target);
		}
		return null;
	}

	public Fightable getTarget() {
		return target;
	}

}
