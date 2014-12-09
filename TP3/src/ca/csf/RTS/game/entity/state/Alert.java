package ca.csf.RTS.game.entity.state;

import ca.csf.RTS.game.entity.controllableEntity.Fightable;
import ca.csf.RTS.game.entity.controllableEntity.Watcher;

public class Alert implements State {

	private Fightable futureTarget;
	private Watcher watcher;

	public Alert(Watcher watcher) {
		this.watcher = watcher;
	}

	@Override
	public StateInteraction action() {
		setFutureTarget((Fightable) watcher.search());
		if (getFutureTarget() != null) {
			return StateInteraction.targetSighted;
		} else {
			return StateInteraction.noTargetSighted;
		}
	}
	
	public Fightable getFutureTarget() {
		return futureTarget;
	}

	public void setFutureTarget(Fightable futurTarget) {
		this.futureTarget = futurTarget;
	}

	
	
}
