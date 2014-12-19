package ca.csf.RTS.upgrades;

import ca.csf.RTS.game.Team;

public class WorkerUpgrade extends Upgrade {
	public void applyUpgrade(Team team) {
		team.getWorkerModel().upgradeHealthMax(30);
	}
}