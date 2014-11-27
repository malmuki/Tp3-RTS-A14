package ca.csf.RTS.game.model.pathFinding;

  public class AStarTile extends DijkstraTile {
    private AStarTile parent;
    private int h;

    AStarTile(int row, int column) {
      super(row, column);
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
 }