package ca.csf.RTS.game.model.pathFinding;

import java.util.ArrayList;
import java.util.Collections;

import org.jsfml.system.Vector2i;

import ca.csf.RTS.game.model.Game;
import ca.csf.RTS.game.model.Tile;

// http://www.policyalmanac.org/games/binaryHeaps.htm
public class PathFinder {
  private Tile[][] map;
  
  private int goalX;
  private int goalY;
  private int startX;
  private int startY;
  
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
    startX = startLocation.getMapLocation().x;
    startY = startLocation.getMapLocation().y;
    goalX = goalLocation.getMapLocation().x;
    goalY = goalLocation.getMapLocation().y;
    
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

      
      //TODO: make the search be complete when the goal has been added to the explored tiles list (closed)
      //Or when there are no more possibilities to explore 
    } while (true); //!closedList.get(0).isExit() && !openList.isEmpty());
  }
  
  private void addValidTileToOpenList(int row, int column, DijkstraTile tile) {
    if (row >= 0 && column >= 0 && row <= Game.MAP_SIZE && column <= Game.MAP_SIZE) { //Tile exists
      //TODO: we need to check if a linked tile is in the closed list
      if (map[row][column].getOnTile() == null && !closedList.contains(map[row][column])) { //It's possible to traverse this Tile and it has not been explored already
        if (openList.contains(map[row][column])) { //If the Tile is already in the possibilities, check if we found a shorter path
          if (map[row][column].getParent().calculateG() > tile.calculateG()) { //If the new path is shorter, change the Tile parent
            map[row][column].setParent(tile);
          }
        } else { //Otherwise just make it a possibility
          openList.add(new DijkstraTile(map[row][column], tile));
        }
      }
    }
  }
}