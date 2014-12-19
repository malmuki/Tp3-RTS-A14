package ca.csf.RTS.game.entity.state;

import ca.csf.RTS.game.entity.Entity;
import ca.csf.RTS.game.entity.controllableEntity.ControlableEntity;
import ca.csf.RTS.game.entity.controllableEntity.Watcher;

public class Alert implements State {

	private Watcher watcher;
	private float alertTimer = 0.0f;

	public Alert(Watcher watcher) {
		this.watcher = watcher;
	}

	@Override
	public StateInteraction action(float deltaTime) {
		alertTimer += deltaTime;
		if (alertTimer > 0.2f) {
			Entity target = watcher.search();
			if (target != null) {
				((ControlableEntity) watcher).setTarget(target);
				return StateInteraction.targetSighted;
			} else {
				return StateInteraction.noTargetSighted;
			}
		} else {
			return StateInteraction.notFinished;
		}
	}

	//@Override
	//public String getStateName() {
	//	return "Alert";
	//}
}