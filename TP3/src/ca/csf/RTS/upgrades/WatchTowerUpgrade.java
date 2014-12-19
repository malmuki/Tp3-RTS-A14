package ca.csf.RTS.upgrades;

import ca.csf.RTS.game.Team;

public class WatchTowerUpgrade {
  public static void applyUpgrade(Team team) {
    team.getWatchTowerModel().upgradeRange(3);
    team.getWatchTowerModel().upgradeDamage(5);
  }
}