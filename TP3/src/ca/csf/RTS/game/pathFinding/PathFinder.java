package ca.csf.RTS.game.pathFinding;

import ca.csf.RTS.game.Game;
import ca.csf.RTS.game.entity.Entity;
import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.controllableEntity.Fighter;
import ca.csf.RTS.game.entity.controllableEntity.building.WatchTower;
import ca.csf.RTS.game.entity.controllableEntity.building.factory.Factory;
import ca.csf.RTS.game.entity.controllableEntity.human.Human;
import ca.csf.RTS.game.entity.controllableEntity.human.Worker;
import ca.csf.RTS.game.entity.state.Attack;
import ca.csf.RTS.game.entity.state.Gathering;
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

		addToOpenList(movingHuman.getTilesOrigin().getMapLocation().x, movingHuman.getTilesOrigin().getMapLocation().y, 0, movingHuman);

		int currentTile;

		do {
			currentTile = openListRemove();

			addToOpenList(listsX[currentTile] - 1, listsY[currentTile] - 1, currentTile, movingHuman);
			addToOpenList(listsX[currentTile], listsY[currentTile] - 1, currentTile, movingHuman);
			addToOpenList(listsX[currentTile] - 1, listsY[currentTile], currentTile, movingHuman);
			addToOpenList(listsX[currentTile] + 1, listsY[currentTile] - 1, currentTile, movingHuman);
			addToOpenList(listsX[currentTile] - 1, listsY[currentTile] + 1, currentTile, movingHuman);
			addToOpenList(listsX[currentTile] + 1, listsY[currentTile], currentTile, movingHuman);
			addToOpenList(listsX[currentTile], listsY[currentTile] + 1, currentTile, movingHuman);
			addToOpenList(listsX[currentTile] + 1, listsY[currentTile] + 1, currentTile, movingHuman);

		} while ((listsX[currentTile] != goalX || listsY[currentTile] != goalY) && openListCount != 0);

		if (movingHuman.getTarget() != null) { // if we are moving to attack, we
												// must not add the final move
			currentTile = listsParent[currentTile];
		}

		boolean pathComplete = false;

		while (!pathComplete) {
			movingHuman.getStateStack().push(new Move(goal, map[listsX[currentTile]][listsY[currentTile]], movingHuman));
			if (listsParent[currentTile] == 0) {
				pathComplete = true;
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

	private static void addToOpenList(int x, int y, int parentID, Human searchingHuman) {
		boolean isAdjacent = false;
		if (x >= 0 && y >= 0 && x < Game.MAP_SIZE && y < Game.MAP_SIZE) { // if tile exists
			int humanX = searchingHuman.getTilesOrigin().getMapLocation().x;
			int humanY = searchingHuman.getTilesOrigin().getMapLocation().y;
			if (humanX - 1 <= x && humanX + 1 >= x && humanY - 1 <= y && humanY + 1 >= y && !(humanX == x && humanY == y)) {
				isAdjacent = true;
			}
			if ((isAdjacent && map[x][y].getOnTile() == null) || (!isAdjacent && !map[x][y].hasObstacle())
					|| map[x][y].getOnTile() == searchingHuman.getTarget()) { // if it's not in the
				if (closedListContains(x, y) == 0) {
					int openListID = openListContains(x, y); // search for the tile in the openList
					if (openListID != 0) { // if the openList already contains the tile, adjust it's parent (if necessary)
						int gMovementCost;
						if (listsX[listsParent[parentID]] != x && listsY[listsParent[parentID]] != y) { // if diagonal with the currently explored tile
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

	private static void reOrderOpenList(int id) {
		int position = 0;
		int temp;

		for (int i = 1; i <= openListCount; i++) {
			if (openList[i] == id) {
				position = i;
			}
		}

		int currentLocation = position;
		while (currentLocation != 1 && listsFCost[openList[currentLocation]] < listsFCost[openList[currentLocation / 2]]) {
			temp = openList[currentLocation];
			openList[currentLocation] = openList[currentLocation / 2];
			openList[currentLocation / 2] = temp;
			currentLocation /= 2;
		}
	}

	private static void addToOpenList(int x, int y, int parentID, int maxG, Human searchingHuman, String searchForTeam) {
		if (x >= 0 && y >= 0 && x < Game.MAP_SIZE && y < Game.MAP_SIZE) { // if tile exists
			// if in range
			if (listsGCost[parentID] <= maxG && closedListContains(x, y) == 0) {
				if (map[x][y].getOnTile() == null || searchForTeam == map[x][y].getOnTile().getTeam().getName() || !map[x][y].hasObstacle()) { // if it's not
																																					// in the
																																					// closed
																																					// list
																																					// and it
																																					// has no
					// obstacle and it's parent's G is not too high
					int openListID = openListContains(x, y); // search for the tile in the openList
					if (openListID != 0) { // if the openList already contains the tile, adjust it's parent (if necessary)
						int gMovementCost;
						if (listsX[listsParent[parentID]] != x && listsY[listsParent[parentID]] != y) { // if diagonal with the currently explored tile
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
	}

	public static Entity findClosestEnnemy(Human searchingHuman, int searchRange) {
		goalX = -1;
		goalY = -1;

		openListCount = 0;
		tilesChecked = 0;

		String searchForTeam;

		if (searchingHuman.getTeam().getName() == "Ennemy") {
			searchForTeam = "Idiot";
		} else {
			searchForTeam = "Ennemy";
		}

		addToOpenList(searchingHuman.getTilesOrigin().getMapLocation().x, searchingHuman.getTilesOrigin().getMapLocation().y, 0, searchRange, searchingHuman,
				searchForTeam);

		Entity onLastTileChecked;
		int currentTile;
		boolean targetSighted = false;

		do {
			currentTile = openListRemove();

			addToOpenList(listsX[currentTile] - 1, listsY[currentTile] - 1, currentTile, searchRange, searchingHuman, searchForTeam);
			addToOpenList(listsX[currentTile], listsY[currentTile] - 1, currentTile, searchRange, searchingHuman, searchForTeam);
			addToOpenList(listsX[currentTile] - 1, listsY[currentTile], currentTile, searchRange, searchingHuman, searchForTeam);
			addToOpenList(listsX[currentTile] + 1, listsY[currentTile] - 1, currentTile, searchRange, searchingHuman, searchForTeam);
			addToOpenList(listsX[currentTile] - 1, listsY[currentTile] + 1, currentTile, searchRange, searchingHuman, searchForTeam);
			addToOpenList(listsX[currentTile] + 1, listsY[currentTile], currentTile, searchRange, searchingHuman, searchForTeam);
			addToOpenList(listsX[currentTile], listsY[currentTile] + 1, currentTile, searchRange, searchingHuman, searchForTeam);
			addToOpenList(listsX[currentTile] + 1, listsY[currentTile] + 1, currentTile, searchRange, searchingHuman, searchForTeam);

			onLastTileChecked = map[listsX[currentTile]][listsY[currentTile]].getOnTile();

			if (onLastTileChecked != null && onLastTileChecked.getTeam().getName() != "Nature"
					&& onLastTileChecked.getTeam().getName() != searchingHuman.getTeam().getName()) {
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

	public static Entity findClosestEnnemy(WatchTower searchingWatchTower, int searchRange) {

		int mapx;
		int mapy;

		for (int i = 2; i <= searchRange; i++) {
			for (int x = -i; x <= 2 * i + searchingWatchTower.getDimention().x; x++) {
				for (int y = -i; y <= 2 * i + searchingWatchTower.getDimention().y; y++) {
					mapx = x + searchingWatchTower.getTilesOrigin().getMapLocation().x + 1;
					mapy = y + searchingWatchTower.getTilesOrigin().getMapLocation().y + 1;

					if ((mapx >= 0 && mapx < Game.MAP_SIZE) && (mapy >= 0 && mapy < Game.MAP_SIZE)) {
						if (map[mapx][mapy].getOnTile() != null && map[mapx][mapy].getOnTile().getTeam() != searchingWatchTower.getTeam()
								&& map[mapx][mapy].getOnTile().getTeam().getName() != "Nature") {
							return map[mapx][mapy].getOnTile();
						}
					}

				}
			}
		}
		return null;
	}

	public static Tile findSpawningSpot(Factory factory) {
		for (int i = factory.getTilesOrigin().getMapLocation().x - 1; i < factory.getTilesOrigin().getMapLocation().x + factory.getDimention().x + 1; i++) {
			for (int j = factory.getTilesOrigin().getMapLocation().y - 1; j < factory.getTilesOrigin().getMapLocation().y + factory.getDimention().y + 1; j++) {
				if ((i >= 0 && i < Game.MAP_SIZE) && (j >= 0 && j < Game.MAP_SIZE)) {
					if (map[i][j].getOnTile() == null) {
						return map[i][j];
					}
				}
			}
		}
		return null;
	}

	public static Entity findClosestRessource(Human searchingHuman, int searchRange) {
		goalX = -1;
		goalY = -1;

		openListCount = 0;
		tilesChecked = 0;

		String searchForTeam = "Nature";

		addToOpenList(searchingHuman.getTilesOrigin().getMapLocation().x, searchingHuman.getTilesOrigin().getMapLocation().y, 0, searchRange, searchingHuman,
				searchForTeam);

		Entity onLastTileChecked;
		int currentTile;
		boolean targetSighted = false;

		do {
			currentTile = openListRemove();

			addToOpenList(listsX[currentTile] - 1, listsY[currentTile] - 1, currentTile, searchRange, searchingHuman, searchForTeam);
			addToOpenList(listsX[currentTile], listsY[currentTile] - 1, currentTile, searchRange, searchingHuman, searchForTeam);
			addToOpenList(listsX[currentTile] - 1, listsY[currentTile], currentTile, searchRange, searchingHuman, searchForTeam);
			addToOpenList(listsX[currentTile] + 1, listsY[currentTile] - 1, currentTile, searchRange, searchingHuman, searchForTeam);
			addToOpenList(listsX[currentTile] - 1, listsY[currentTile] + 1, currentTile, searchRange, searchingHuman, searchForTeam);
			addToOpenList(listsX[currentTile] + 1, listsY[currentTile], currentTile, searchRange, searchingHuman, searchForTeam);
			addToOpenList(listsX[currentTile], listsY[currentTile] + 1, currentTile, searchRange, searchingHuman, searchForTeam);
			addToOpenList(listsX[currentTile] + 1, listsY[currentTile] + 1, currentTile, searchRange, searchingHuman, searchForTeam);

			onLastTileChecked = map[listsX[currentTile]][listsY[currentTile]].getOnTile();

			if (onLastTileChecked != null && onLastTileChecked.getTeam().getName() == "Nature") {
				targetSighted = true;
			}

		} while (!targetSighted && openListCount != 0);

		if (targetSighted) {
			searchingHuman.getStateStack().clear();
			searchingHuman.getStateStack().push(new Gathering((Worker) searchingHuman));

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