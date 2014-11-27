package ca.csf.RTS.game.model.pathFinding;

  public class AStarTile extends DijkstraTile {
    private AStarTile parent;
    private int g;
    private int h;

    AStarTile(int row, int column) {
      super(row, column);
      g = 0;
      h = 0;
      parent = null;
    }

    public void setParent(AStarTile tile) {
      parent = tile;
      g = calculateG();
      h = calculateH();
    }

    public int getRow() {
      return ROW;
    }

    public int getColumn() {
      return COLUMN;
    }

    public void makeOpen() {
      h = calculateH();
    }

    public AStarTile getParent() {
      return parent;
    }

    public int calculateF() {
      return g + h;
    }

    private int calculateH() {
        int h = (ROW - 8) * 10;
        int temp = (COLUMN - 8) * 10;

        if (h < 0) {
          h *= -1;
        }

        if (temp < 0) {
          temp *= -1;
        }

        h += temp;
        return h;
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
 }