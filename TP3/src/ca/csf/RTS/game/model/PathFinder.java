package ca.csf.RTS.game.model;

import org.jsfml.system.Vector2i;

// http://www.policyalmanac.org/games/binaryHeaps.htm
public class PathFinder {
  private Tile[][] map;
  
  private int squaresChecked;
  private int openListCount;
  private int[] openListID;
  
  private int goalX;
  private int goalY;
  private int startX;
  private int startY;
  
  private int[] openListFCost;
  private int[] openListX;
  private int[] openListY;
  
  PathFinder(Tile[][] map) {
    this.map = map; //TODO: maybe this is not needed
    openListID = new int[Game.MAP_SIZE * Game.MAP_SIZE];
    openListFCost = new int[Game.MAP_SIZE * Game.MAP_SIZE];
    openListX= new int[Game.MAP_SIZE * Game.MAP_SIZE];
    openListY = new int[Game.MAP_SIZE * Game.MAP_SIZE];
  }
  
  public void findPath(Vector2i startLocation, Vector2i goalLocation) { //TODO: change to return to path
    startX = startLocation.x;
    startY = startLocation.y;
    goalX = goalLocation.x;
    goalY = goalLocation.y;
    
    openListCount = 0;
    
    //TODO: Commence by adding Start to the path possibility, manually for NOW
    openListID[1] = 1;
    openListCount++;
    openListX[openListID[1]] = goalX;
    openListY[openListID[1]] = goalY;
    //openListAdd(startLocation.x, startLocation.y);
    
    //It's the only Tile with no parents
    //start.setParent(null);
    
    Tile tile;
    do {
      //TODO: remove and get the removed tile
      openListRemove();
      
      //TODO: add to closed list
      //closedList.add(0, tile);
      
      //TODO: Add all surrounding tiles to the path possibility if they meet condiitons

      //TODO: make the search be complete when the goal has been added to the explored tiles list (closed)
      //Or when there are no more possibilities to explore 
    } while (true); //!closedList.get(0).isExit() && !openList.isEmpty());
  }
  
  public void findClosest(Vector2i startLocation) { //Dijkstra //TODO: change to return to path
    //TODO: everything
  }
  
  public void findClosest(Vector2i startLocation, int limit) { //Dijkstra with limits //TODO: change to return to path
  //TODO: everything
  }
  
  private void openListAdd(int row, int column) {
    int g = 0; //TODO: find a way to link each tile to it's parent
    int h = calculateH(row, column);

    squaresChecked++;
    openListCount++;
    openListID[openListCount] = squaresChecked;
    
    openListX[openListID[openListCount]] = row;
    openListY[openListID[openListCount]] = column;
    openListFCost[openListID[openListCount]] = g + h;
    
    
    int currentPosition = openListCount;
    while (currentPosition != 1 && openListFCost[currentPosition] >= openListFCost[currentPosition/2]) {
      int temp = openListID[currentPosition/2];
      openListID[currentPosition/2] = openListID[currentPosition];
      openListID[currentPosition] = temp;
      currentPosition /= 2;
    }
  }
  
  private void openListRemove() { //Should work
    openListID[1] = openListCount;
    openListCount--;
    
    int currentPosition = 1;
    int nextPosition;
    do {
      nextPosition = currentPosition;
      
      int temp = openListID[nextPosition]; //TODO: maybe test to avoid doing this the first time?
      openListID[nextPosition] = openListID[currentPosition];
      openListID[currentPosition] = temp;
      
      if (2*nextPosition <= openListCount) {
        if (openListFCost[openListID[nextPosition]] >= openListFCost[2*nextPosition]) {
          currentPosition = 2*nextPosition;
        }
        if (openListFCost[openListID[nextPosition]] >= openListFCost[2*nextPosition + 1]) {
          currentPosition = 2*nextPosition + 1;
        }
      } else if (2*nextPosition <= openListCount) {
        currentPosition = 2*nextPosition;
      }
      
    } while (currentPosition != nextPosition);
  }
  
  private void resortOpenList(int x, int y) {
  //TODO: everything
  }
  
  private int calculateH(int tileX, int tileY) {
    int h = (tileX - goalX) * 10;
    int temp = (tileY - goalY) * 10;

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