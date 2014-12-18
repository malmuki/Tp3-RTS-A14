package ca.csf.RTS.upgrades;

import ca.csf.RTS.game.Team;

public class FootmanUpgrade extends Upgrade {
  
  public void applyUpgrade(Team team) {
    team.getFootManModel().upgradeDamage(5);
    team.getFootManModel().upgradeHealthMax(20);
  }
}