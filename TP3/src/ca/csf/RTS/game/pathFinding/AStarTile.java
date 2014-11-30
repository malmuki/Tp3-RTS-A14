package ca.csf.RTS.game.pathFinding;

import ca.csf.RTS.game.entity.Tile;

  public class AStarTile extends DijkstraTile {
    private AStarTile parent;
    private int h;

    AStarTile(Tile mapTile, DijkstraTile parent) {
      super(mapTile, parent);
      h = calculateH();
      parent = null;
    }

    public void setParent(AStarTile tile) {
      parent = tile;
      g = calculateG();
      h = calculateH();
    }

    public AStarTile getParent() {
      return parent;
    }

    public int calculateF() {
      return g + h;
    }

    private int calculateH() {
        int h = (mapTile.getMapLocation().x - 8) * 10; //TODO: replace 8
        int temp = (mapTile.getMapLocation().y - 8) * 10; //TODO: replace 8

        if (h < 0) {
          h *= -1;
        }

        if (temp < 0) {
          temp *= -1;
        }

        h += temp;
        return h;
     }
 }