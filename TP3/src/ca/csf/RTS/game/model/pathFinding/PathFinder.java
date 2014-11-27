package ca.csf.RTS.game.model.pathFinding;

import java.util.ArrayList;

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
  
  PathFinder(Tile[][] map) {
    this.map = map; //TODO: maybe this is not needed
    openList = new ArrayList<DijkstraTile>();
  }
  
  public void findPath(Vector2i startLocation, Vector2i goalLocation) { //TODO: change to return to path
    startX = startLocation.x;
    startY = startLocation.y;
    goalX = goalLocation.x;
    goalY = goalLocation.y;
    
    //TODO: Commence by adding Start to the path possibility, manually for NOW
    
    //It's the only Tile with no parents
    //start.setParent(null);
    
    do {
      //TODO: remove and get the removed tile
      
      //TODO: add to closed list
      //closedList.add(0, tile);
      
      //TODO: Add all surrounding tiles to the path possibility if they meet condiitons
      
      
      //TODO: make the search be complete when the goal has been added to the explored tiles list (closed)
      //Or when there are no more possibilities to explore 
    } while (true); //!closedList.get(0).isExit() && !openList.isEmpty());
  }
}