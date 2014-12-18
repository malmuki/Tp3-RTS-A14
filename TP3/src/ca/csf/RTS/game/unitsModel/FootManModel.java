package ca.csf.RTS.game.unitsModel;


public class FootManModel extends Model {
  private int range = 1;
  private int damage = 10;
  private float attackDelay = 1.0f;
  
  public FootManModel() {
    healthMax = 100;
  }

  public int getRange() {
    return range;
  }

  public int getDamage() {
    return damage;
  }

  public float getAttackDelay() {
    return attackDelay;
  }

  public void upgradeRange(int amount) {
    range += amount;
  }

  public void upgradeDamage(int amount) {
    damage += amount;
  }

  public void upgradeAttackDelay(float amount) {
    attackDelay -= amount;
  }

}