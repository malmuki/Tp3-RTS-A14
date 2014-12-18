package ca.csf.RTS.upgrades;

import ca.csf.RTS.game.Team;

public class WorkerUpgrade {
  public static void applyUpgrade(Team team) {
    team.getWorkerModel().upgradeHealthMax(30);
  }
}