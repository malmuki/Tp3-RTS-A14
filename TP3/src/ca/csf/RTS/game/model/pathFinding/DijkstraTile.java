package ca.csf.RTS.game.model.pathFinding;

public class DijkstraTile implements Comparable<DijkstraTile> {
  protected DijkstraTile parent;
  protected final int ROW;
  protected final int COLUMN;
  protected int g;
  protected int h;

  public DijkstraTile(int row, int column) {
    g = 0;
    h = 0;
    ROW = row;
    COLUMN = column;
    parent = null;
  }

  public void setParent(AStarTile tile) {
    parent = tile;
    g = calculateG();
  }

  public int getRow() {
    return ROW;
  }

  public int getColumn() {
    return COLUMN;
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
    } else if (parent.getRow() != ROW && parent.getColumn() != COLUMN) {
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