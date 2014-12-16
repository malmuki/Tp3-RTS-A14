package ca.csf.RTS.game.pathFinding;

import ca.csf.RTS.game.Game;
import ca.csf.RTS.game.entity.Entity;
import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.controllableEntity.Fighter;
import ca.csf.RTS.game.entity.controllableEntity.human.Human;
import ca.csf.RTS.game.entity.state.Attack;
import ca.csf.RTS.game.entity.state.Move;

public class PathFinder {
	// To make path finding faster, I use a system called binary heap.
	// The idea and tutorial came from this web site:
	// http://www.policyalmanac.org/games/binaryHeaps.htm
	// It uses more memory because of the arrays below that are constantly
	// maintained but it's
	// (supposedly) 2 -3 times faster than having ArrayLists that you keep
	// sorted, which is really
	// needed to move multiple units a long distance and to have units on alert
	// constantly

	private static Tile[][] map;

	private static int[] closedList = new int[Game.MAP_SIZE * Game.MAP_SIZE];
	private static int[] openList = new int[Game.MAP_SIZE * Game.MAP_SIZE];

	private static int[] listsParent = new int[Game.MAP_SIZE * Game.MAP_SIZE];
	private static int[] listsX = new int[Game.MAP_SIZE * Game.MAP_SIZE];
	private static int[] listsY = new int[Game.MAP_SIZE * Game.MAP_SIZE];
	private static int[] listsFCost = new int[Game.MAP_SIZE * Game.MAP_SIZE];
	private static int[] listsGCost = new int[Game.MAP_SIZE * Game.MAP_SIZE];
	private static int[] listsHCost = new int[Game.MAP_SIZE * Game.MAP_SIZE];

	private static int openListCount;
	private static int tilesChecked; // is equal to closedListCount +
										// openListCount

	private static int goalX;
	private static int goalY;

	public static void initialisePathFinder(Tile[][] map) {
		PathFinder.map = map;
	}

	public static void findPath(Human movingHuman, Tile goal) {
		goalX = goal.getMapLocation().x;
		goalY = goal.getMapLocation().y;

		openListCount = 0;
		tilesChecked = 0;

		addToOpenList(movingHuman.getTilesOrigin().getMapLocation().x, movingHuman.getTilesOrigin().getMapLocation().y, 0);

		int currentTile;

		do {
			currentTile = openListRemove();

			addToOpenList(listsX[currentTile] - 1, listsY[currentTile] - 1, currentTile);
			addToOpenList(listsX[currentTile], listsY[currentTile] - 1, currentTile);
			addToOpenList(listsX[currentTile] - 1, listsY[currentTile], currentTile);
			addToOpenList(listsX[currentTile] + 1, listsY[currentTile] - 1, currentTile);
			addToOpenList(listsX[currentTile] - 1, listsY[currentTile] + 1, currentTile);
			addToOpenList(listsX[currentTile] + 1, listsY[currentTile], currentTile);
			addToOpenList(listsX[currentTile], listsY[currentTile] + 1, currentTile);
			addToOpenList(listsX[currentTile] + 1, listsY[currentTile] + 1, currentTile);

		} while ((listsX[currentTile] != goalX || listsY[currentTile] != goalY) && openListCount != 0);

		if (movingHuman.getTarget() != null) { // if we are moving to attack, we
												// must not add the final move
			currentTile = listsParent[currentTile];
		}

		boolean pathComplete = true;

		while (pathComplete) {
			movingHuman.getStateStack().push(new Move(goal, map[listsX[currentTile]][listsY[currentTile]], movingHuman));
			if (listsParent[currentTile] == 0) {
				pathComplete = false;
			} else {
				currentTile = listsParent[currentTile];
			}
		}
		movingHuman.getStateStack().pop();
	}

	private static int openListRemove() {
		if (openListCount == 0) {
			return 0;
		} else {
			closedList[tilesChecked - openListCount + 1] = openList[1];

			openList[1] = openList[openListCount];
			openListCount--;

			int newPosition = 1;
			int currentPosition;
			boolean orderIsRetablished = false;
			int temp;

			while (!orderIsRetablished) {
				currentPosition = newPosition;
				if (2 * currentPosition + 1 <= openListCount) {
					if (listsFCost[openList[currentPosition]] >= listsFCost[openList[2 * currentPosition]]) {
						newPosition = 2 * currentPosition;
					}
					if (listsFCost[openList[newPosition]] >= listsFCost[openList[2 * currentPosition + 1]]) {
						newPosition = 2 * currentPosition + 1;
					}
				} else if (2 * currentPosition <= openListCount) {
					if (listsFCost[openList[currentPosition]] >= listsFCost[openList[2 * currentPosition]]) {
						newPosition = 2 * currentPosition;
					}
				}

				if (currentPosition != newPosition) {
					temp = openList[currentPosition];
					openList[currentPosition] = openList[newPosition];
					openList[newPosition] = temp;
				} else {
					orderIsRetablished = true;
				}
			}

			return closedList[tilesChecked - openListCount];
		}
	}

	private static void addToOpenList(int x, int y, int parentID) {
		if (x >= 0 && y >= 0 && x < Game.MAP_SIZE && y < Game.MAP_SIZE) { // if tile exists
			if (!map[x][y].hasObstacle() && closedListContains(x, y) == 0) { // if it's not in the closed list and it has no obstacle
				int openListID = openListContains(x, y); // search for the tile in the openList
				if (openListID != 0) { // if the openList already contains the tile, adjust it's parent (if necessary)
					int gMovementCost;
					if (listsX[listsParent[parentID]] != x && listsY[listsParent[parentID]] != y) { //if diagonal with the currently explored tile
						gMovementCost = 14;
					} else {
						gMovementCost = 10;
					}
					if (listsGCost[openListID] > listsGCost[parentID] + gMovementCost) {
						listsParent[openListID] = parentID;
						listsGCost[openListID] = calculateG(openListID);
						reOrderOpenList(openListID);
					}
				} else { // otherwise just add it to the list
					openListCount++;
					tilesChecked++;
					openList[openListCount] = tilesChecked;
					listsX[tilesChecked] = x;
					listsY[tilesChecked] = y;
					listsParent[tilesChecked] = parentID;
					listsGCost[tilesChecked] = calculateG(tilesChecked);
					listsHCost[tilesChecked] = calculateH(x, y);
					listsFCost[tilesChecked] = listsGCost[tilesChecked] + listsHCost[tilesChecked];

					int currentLocation = openListCount;
					int temp;

					while (currentLocation != 1 && listsFCost[openList[currentLocation]] < listsFCost[openList[currentLocation / 2]]) {
						temp = openList[currentLocation];
						openList[currentLocation] = openList[currentLocation / 2];
						openList[currentLocation / 2] = temp;
						currentLocation /= 2;
					}
				}
			}
		}
	}

	private static int closedListContains(int x, int y) {
		for (int i = 1; i <= tilesChecked - openListCount; i++) {
			if (listsX[closedList[i]] == x && listsY[closedList[i]] == y) {
				return closedList[i];
			}
		}
		return 0;
	}

	private static int openListContains(int x, int y) {
		for (int i = 1; i <= openListCount; i++) {
			if (listsX[openList[i]] == x && listsY[openList[i]] == y) {
				return openList[i];
			}
		}
		return 0;
	}

	private static int calculateG(int tileID) {
		if (listsParent[tileID] != 0) {
			if (listsX[listsParent[tileID]] != listsX[tileID] && listsY[listsParent[tileID]] != listsY[tileID]) {
				return 14 + listsGCost[listsParent[tileID]];
			} else {
				return 10 + listsGCost[listsParent[tileID]];
			}
		} else {
			return 0;
		}
	}

	private static int calculateH(int tileX, int tileY) {
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
	
	private static void reOrderOpenList(int id) { //TODO: this
		
	}

	private static void addToOpenList(int x, int y, int parentID, int maxG) {
		if (x >= 0 && y >= 0 && x < Game.MAP_SIZE && y < Game.MAP_SIZE) { // if tile exists
			if (!map[x][y].hasObstacle() && closedListContains(x, y) == 0 && listsGCost[parentID] <= maxG) { // if it's not in the closed list and it has no
																												// obstacle and it's parent's G is not too high
				int openListID = openListContains(x, y); // search for the tile in the openList
				if (openListID != 0) { // if the openList already contains the tile, adjust it's parent (if necessary)
					int gMovementCost;
					if (listsX[listsParent[parentID]] != x && listsY[listsParent[parentID]] != y) { //if diagonal with the currently explored tile
						gMovementCost = 14;
					} else {
						gMovementCost = 10;
					}
					if (listsGCost[openListID] > listsGCost[parentID] + gMovementCost) {
						listsParent[openListID] = parentID;
						listsGCost[openListID] = calculateG(openListID);
						reOrderOpenList(openListID);
					}
				} else { // otherwise just add it to the list
					openListCount++;
					tilesChecked++;
					openList[openListCount] = tilesChecked;
					listsX[tilesChecked] = x;
					listsY[tilesChecked] = y;
					listsParent[tilesChecked] = parentID;
					listsGCost[tilesChecked] = calculateG(tilesChecked);
					listsFCost[tilesChecked] = listsGCost[tilesChecked];

					int currentLocation = openListCount;
					int temp;

					while (currentLocation != 1 && listsFCost[openList[currentLocation]] < listsFCost[openList[currentLocation / 2]]) {
						temp = openList[currentLocation];
						openList[currentLocation] = openList[currentLocation / 2];
						openList[currentLocation / 2] = temp;
						currentLocation /= 2;
					}
				}
			}
		}
	}

	public static Entity findClosestEnnemy(Human searchingHuman, int searchRange) {
		goalX = -1;
		goalY = -1;

		openListCount = 0;
		tilesChecked = 0;

		addToOpenList(searchingHuman.getTilesOrigin().getMapLocation().x, searchingHuman.getTilesOrigin().getMapLocation().y, 0, searchRange);

		Entity onLastTileChecked;
		int currentTile;
		boolean targetSighted = false;

		do {
			currentTile = openListRemove();

			addToOpenList(listsX[currentTile] - 1, listsY[currentTile] - 1, currentTile, searchRange);
			addToOpenList(listsX[currentTile], listsY[currentTile] - 1, currentTile, searchRange);
			addToOpenList(listsX[currentTile] - 1, listsY[currentTile], currentTile, searchRange);
			addToOpenList(listsX[currentTile] + 1, listsY[currentTile] - 1, currentTile, searchRange);
			addToOpenList(listsX[currentTile] - 1, listsY[currentTile] + 1, currentTile, searchRange);
			addToOpenList(listsX[currentTile] + 1, listsY[currentTile], currentTile, searchRange);
			addToOpenList(listsX[currentTile], listsY[currentTile] + 1, currentTile, searchRange);
			addToOpenList(listsX[currentTile] + 1, listsY[currentTile] + 1, currentTile, searchRange);

			onLastTileChecked = map[listsX[currentTile]][listsY[currentTile]].getOnTile();

			if (onLastTileChecked != null && onLastTileChecked.getTeam().getName() != "Nature" && onLastTileChecked.getTeam().getName() != searchingHuman.getTeam().getName()) {
				targetSighted = true;
			}

		} while (!targetSighted && openListCount != 0);

		if (targetSighted) {
			searchingHuman.getStateStack().clear();
			searchingHuman.getStateStack().push(new Attack((Fighter) searchingHuman));
			
			currentTile = listsParent[currentTile];

			boolean pathComplete = true;

			while (pathComplete) {
				searchingHuman.getStateStack()
						.push(new Move(onLastTileChecked.getTilesOrigin(), map[listsX[currentTile]][listsY[currentTile]], searchingHuman));
				if (listsParent[currentTile] == 0) {
					pathComplete = false;
				} else {
					currentTile = listsParent[currentTile];
				}
			}
			searchingHuman.getStateStack().pop();

			return onLastTileChecked;
		} else {
			return null;
		}
	}
}