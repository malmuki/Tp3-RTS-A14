package ca.csf.RTS.game.pathFinding;

import java.util.ArrayList;
import java.util.Collections;

import ca.csf.RTS.game.Game;
import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.controllableEntity.human.Human;
import ca.csf.RTS.game.entity.state.Move;

// http://www.policyalmanac.org/games/binaryHeaps.htm
public class PathFinder {
  private static Tile[][] map;
  private static ArrayList<DijkstraTile> openList = new ArrayList<DijkstraTile>();
  private static ArrayList<DijkstraTile> closedList = new ArrayList<DijkstraTile>();
  
  public static void setMap(Tile[][] map) {
    PathFinder.map = map;
  }
  
  public static void findPath(Human movingHuman, Tile goalLocation) {
    openList.clear();
    closedList.clear();
    
    openList.add(new AStarTile(movingHuman.getCurrentTiles().get(0), null));
    
    AStarTile currentTile;
    
    do {
      currentTile = (AStarTile)Collections.min(openList); //Get lowest F cost tile
      
      openList.remove(currentTile);
      closedList.add(0, currentTile);
      
      addValidAStarTileToOpenList(currentTile.getMapTile().getMapLocation().x - 1, currentTile.getMapTile().getMapLocation().y, currentTile);
      addValidAStarTileToOpenList(currentTile.getMapTile().getMapLocation().x - 1, currentTile.getMapTile().getMapLocation().y - 1, currentTile);
      addValidAStarTileToOpenList(currentTile.getMapTile().getMapLocation().x - 1, currentTile.getMapTile().getMapLocation().y + 1, currentTile);
      addValidAStarTileToOpenList(currentTile.getMapTile().getMapLocation().x, currentTile.getMapTile().getMapLocation().y - 1, currentTile);
      addValidAStarTileToOpenList(currentTile.getMapTile().getMapLocation().x, currentTile.getMapTile().getMapLocation().y + 1, currentTile);
      addValidAStarTileToOpenList(currentTile.getMapTile().getMapLocation().x + 1, currentTile.getMapTile().getMapLocation().y - 1, currentTile);
      addValidAStarTileToOpenList(currentTile.getMapTile().getMapLocation().x + 1, currentTile.getMapTile().getMapLocation().y, currentTile);
      addValidAStarTileToOpenList(currentTile.getMapTile().getMapLocation().x + 1, currentTile.getMapTile().getMapLocation().y + 1, currentTile);      
    } while (closedList.get(0).mapTile != goalLocation && !openList.isEmpty());
    
    movingHuman.getStateStack().clear();
    
    if (openList.isEmpty()) {
      movingHuman.getStateStack().add(movingHuman.getDefaultState());
    } else {
      DijkstraTile lastTileAdded = closedList.get(0);
      do {
        movingHuman.getStateStack().add(0, new Move(goalLocation, lastTileAdded.getMapTile(), movingHuman));
        if (lastTileAdded.getParent() == null) {
          break;
        } else {
          lastTileAdded = lastTileAdded.getParent();
        }
      } while (true);
    }
  }
  
//  public void findClosest(Tile startLocation, Object objectToFind) {
//    openList.clear();
//    closedList.clear();
//    
//    openList.add(new DijkstraTile(startLocation, null));
//    
//    DijkstraTile currentTile;
//    
//    do {
//      currentTile = chooseLowestF();
//      openList.remove(currentTile);
//      closedList.add(0, currentTile);
//      
//      addValidDijkstraTileToOpenList(currentTile.getMapTile().getMapLocation().x - 1, currentTile.getMapTile().getMapLocation().y, currentTile);
//      addValidDijkstraTileToOpenList(currentTile.getMapTile().getMapLocation().x - 1, currentTile.getMapTile().getMapLocation().y - 1, currentTile);
//      addValidDijkstraTileToOpenList(currentTile.getMapTile().getMapLocation().x - 1, currentTile.getMapTile().getMapLocation().y + 1, currentTile);
//      addValidDijkstraTileToOpenList(currentTile.getMapTile().getMapLocation().x, currentTile.getMapTile().getMapLocation().y - 1, currentTile);
//      addValidDijkstraTileToOpenList(currentTile.getMapTile().getMapLocation().x, currentTile.getMapTile().getMapLocation().y + 1, currentTile);
//      addValidDijkstraTileToOpenList(currentTile.getMapTile().getMapLocation().x + 1, currentTile.getMapTile().getMapLocation().y - 1, currentTile);
//      addValidDijkstraTileToOpenList(currentTile.getMapTile().getMapLocation().x + 1, currentTile.getMapTile().getMapLocation().y, currentTile);
//      addValidDijkstraTileToOpenList(currentTile.getMapTile().getMapLocation().x + 1, currentTile.getMapTile().getMapLocation().y + 1, currentTile);      
//    } while (closedList.get(0).mapTile.getOnTile() != objectToFind && !openList.isEmpty());
//  }
  
  private static void addValidDijkstraTileToOpenList(int row, int column, DijkstraTile tile) {
    if (row >= 0 && column >= 0 && row <= Game.MAP_SIZE && column <= Game.MAP_SIZE) {
      DijkstraTile checkTile = getTile(row, column, closedList);
      if (map[row][column].getOnTile() == null && checkTile != null) {
        checkTile = getTile(row, column, openList);
        if (checkTile != null) {
          if (checkTile.getParent().calculateG() > tile.calculateG()) {
            checkTile.setParent(tile);
          }
        } else {
          openList.add(new DijkstraTile(map[row][column], tile));
        }
      }
    }
  }
  
  private static void addValidAStarTileToOpenList(int row, int column, DijkstraTile tile) {
    if (row >= 0 && column >= 0 && row <= Game.MAP_SIZE && column <= Game.MAP_SIZE) { //Tile exists
      DijkstraTile checkTile = getTile(row, column, closedList);
      if (map[row][column].getOnTile() == null && checkTile != null) { //NullPointerException
        checkTile = getTile(row, column, openList);
        if (checkTile != null) {
          if (checkTile.getParent().calculateG() > tile.calculateG()) {
            checkTile.setParent(tile);
          }
        } else {
          openList.add(new AStarTile(map[row][column], tile));
        }
      }
    }
  }
  
  private static DijkstraTile getTile(int row, int column, ArrayList<DijkstraTile> list) {
    DijkstraTile tile = null;
    for (DijkstraTile i : list) {
      if (i.getMapTile().getMapLocation().x == row && i.getMapTile().getMapLocation().y == column) {
        tile = i;
        break;
      }
    }
    return tile;
  }
}