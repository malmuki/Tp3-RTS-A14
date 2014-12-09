package ca.csf.RTS.game.pathFinding;

import ca.csf.RTS.game.entity.Tile;

public class DijkstraTile implements Comparable<DijkstraTile> {
  protected DijkstraTile parent;
  protected Tile mapTile;
  protected int g;
  protected int h;

  public DijkstraTile(Tile mapTile, DijkstraTile parent) {
    g = 0;
    this.parent = parent;
    this.mapTile  = mapTile;
    parent = null;
  }

  public void setParent(DijkstraTile tile) {
    parent = tile;
    g = calculateG();
  }

  public Tile getMapTile() {
    return mapTile;
  }
  
  public DijkstraTile getParent() {
    return parent;
  }

  public int calculateF() {
    return g;
  }

  public int calculateG() {
    int newG;
    if (parent == null) {
      newG = 0;
    } else if (parent.getMapTile().getMapLocation().x != mapTile.getMapLocation().x && mapTile.getMapLocation().y != parent.getMapTile().getMapLocation().y) {
      newG = 14 + parent.g;
    } else {
      newG = 10 + parent.g;
    }
    return newG;
  }

  @Override
  public int compareTo(DijkstraTile tile) {
    return this.calculateF() - tile.calculateF();
 }
}