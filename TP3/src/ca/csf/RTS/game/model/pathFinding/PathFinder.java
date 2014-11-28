package ca.csf.RTS.game.model.pathFinding;

import java.util.ArrayList;
import java.util.Collections;

import org.jsfml.system.Vector2i;

import ca.csf.RTS.game.model.Game;
import ca.csf.RTS.game.model.Tile;

// http://www.policyalmanac.org/games/binaryHeaps.htm
public class PathFinder {
  private Tile[][] map;
  
  ArrayList<DijkstraTile> openList;
  ArrayList<DijkstraTile> closedList;
  
  PathFinder(Tile[][] map) {
    this.map = map;
    openList = new ArrayList<DijkstraTile>();
    closedList = new ArrayList<DijkstraTile>();
  }
  
  private DijkstraTile chooseLowestF() {
    return Collections.min(openList);
  }
  
  public void findPath(Tile startLocation, Tile goalLocation) { //TODO: change to return to path
    
    openList.add(new AStarTile(startLocation, null));
    
    DijkstraTile currentTile;
    
    do {
      currentTile = chooseLowestF();
      openList.remove(currentTile);
      closedList.add(0, currentTile);
      
      //TODO: Add all surrounding tiles to the path possibility if they meet condiitons
      addValidTileToOpenList(currentTile.getMapTile().getMapLocation().x - 1, currentTile.getMapTile().getMapLocation().y, currentTile);
      addValidTileToOpenList(currentTile.getMapTile().getMapLocation().x - 1, currentTile.getMapTile().getMapLocation().y - 1, currentTile);
      addValidTileToOpenList(currentTile.getMapTile().getMapLocation().x - 1, currentTile.getMapTile().getMapLocation().y + 1, currentTile);
      addValidTileToOpenList(currentTile.getMapTile().getMapLocation().x, currentTile.getMapTile().getMapLocation().y - 1, currentTile);
      addValidTileToOpenList(currentTile.getMapTile().getMapLocation().x, currentTile.getMapTile().getMapLocation().y + 1, currentTile);
      addValidTileToOpenList(currentTile.getMapTile().getMapLocation().x + 1, currentTile.getMapTile().getMapLocation().y - 1, currentTile);
      addValidTileToOpenList(currentTile.getMapTile().getMapLocation().x + 1, currentTile.getMapTile().getMapLocation().y, currentTile);
      addValidTileToOpenList(currentTile.getMapTile().getMapLocation().x + 1, currentTile.getMapTile().getMapLocation().y + 1, currentTile);      
    } while (closedList.get(0).mapTile != goalLocation && !openList.isEmpty());
  }
  
  private void addValidTileToOpenList(int row, int column, DijkstraTile tile) {
    if (row >= 0 && column >= 0 && row <= Game.MAP_SIZE && column <= Game.MAP_SIZE) { //Tile exists
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
  
  private DijkstraTile getTile(int row, int column, ArrayList<DijkstraTile> list) {
    DijkstraTile tile = null;
    for (DijkstraTile i : list) {
      if (i.getMapTile().getMapLocation().x == row && i.getMapTile().getMapLocation().y == column) {
        tile = i;
      }
    }
    return tile;
  }
}