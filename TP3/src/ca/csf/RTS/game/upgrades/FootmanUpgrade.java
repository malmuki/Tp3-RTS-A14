package ca.csf.RTS.game.upgrades;

import ca.csf.RTS.game.Team;

public class FootmanUpgrade {
	public static void applyUpgrade(Team team) {
		team.getFootManModel().upgradeDamage(5);
		team.getFootManModel().upgradeHealthMax(20);
	}
}