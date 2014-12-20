package ca.csf.RTS.game.upgrades;

import ca.csf.RTS.game.Team;

public class WorkerUpgrade {
	public static void applyUpgrade(Team team) {
		team.getWorkerModel().upgradeHealthMax(30);
	}
}