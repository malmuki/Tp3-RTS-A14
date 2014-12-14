package ca.csf.RTS.game.pathFinding;

import java.util.ArrayList;
import java.util.Collections;

import ca.csf.RTS.game.Game;
import ca.csf.RTS.game.entity.Entity;
import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.controllableEntity.Fighter;
import ca.csf.RTS.game.entity.controllableEntity.human.Human;
import ca.csf.RTS.game.entity.state.Attack;
import ca.csf.RTS.game.entity.state.Move;

// http://www.policyalmanac.org/games/binaryHeaps.htm
public class PathFinder {
	private static Tile goal;

	public static Tile getGoal() {
		return goal;
	}

	private static Tile[][] map;
	private static ArrayList<DijkstraTile> openList = new ArrayList<DijkstraTile>();
	private static ArrayList<DijkstraTile> closedList = new ArrayList<DijkstraTile>();

	public static void setMap(Tile[][] map) {
		PathFinder.map = map;
	}

	public static void findPath(Human movingHuman, Tile _goal) {
		goal = _goal;

		openList.clear();
		closedList.clear();

		openList.add(new AStarTile(movingHuman.getTilesOrigin(), null));

		AStarTile currentTile;

		do {
			currentTile = (AStarTile) Collections.min(openList); // Get lowest F cost tile

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
		} while (closedList.get(0).getMapTile() != goal && !openList.isEmpty());

		AStarTile lastTileAdded = (AStarTile) closedList.get(0);
		if (movingHuman.getTarget() != null) { // if we are moving to attack, we must not add the final tile
			lastTileAdded = (AStarTile) lastTileAdded.getParent();
		}

		boolean temp = true;
		while (temp) {
			movingHuman.getStateStack().push(new Move(goal, lastTileAdded.getMapTile(), movingHuman));
			if (lastTileAdded.getParent() == null) {
				temp = false;
			} else {
				lastTileAdded = (AStarTile) lastTileAdded.getParent();
			}
		}
		movingHuman.getStateStack().pop();
	}
	
	public static Entity findClosestEnnemy(Human searchingHuman, int searchRange) {
		goal = null;

		openList.clear();
		closedList.clear();

		openList.add(new DijkstraTile(searchingHuman.getTilesOrigin(), null));

		DijkstraTile currentTile;
		boolean targetSighted = false;
		Entity onLastTileChecked;
		
		do {
			currentTile = Collections.min(openList); // Get lowest F cost tile

			openList.remove(currentTile);
			closedList.add(0, currentTile);

			
			addValidDijkstraTileToOpenList(currentTile.getMapTile().getMapLocation().x - 1, currentTile.getMapTile().getMapLocation().y, currentTile, searchRange);
			addValidDijkstraTileToOpenList(currentTile.getMapTile().getMapLocation().x - 1, currentTile.getMapTile().getMapLocation().y - 1, currentTile, searchRange);
			addValidDijkstraTileToOpenList(currentTile.getMapTile().getMapLocation().x - 1, currentTile.getMapTile().getMapLocation().y + 1, currentTile, searchRange);
			addValidDijkstraTileToOpenList(currentTile.getMapTile().getMapLocation().x, currentTile.getMapTile().getMapLocation().y - 1, currentTile, searchRange);
			addValidDijkstraTileToOpenList(currentTile.getMapTile().getMapLocation().x, currentTile.getMapTile().getMapLocation().y + 1, currentTile, searchRange);
			addValidDijkstraTileToOpenList(currentTile.getMapTile().getMapLocation().x + 1, currentTile.getMapTile().getMapLocation().y - 1, currentTile, searchRange);
			addValidDijkstraTileToOpenList(currentTile.getMapTile().getMapLocation().x + 1, currentTile.getMapTile().getMapLocation().y, currentTile, searchRange);
			addValidDijkstraTileToOpenList(currentTile.getMapTile().getMapLocation().x + 1, currentTile.getMapTile().getMapLocation().y + 1, currentTile, searchRange);
			onLastTileChecked = closedList.get(0).getMapTile().getOnTile();
			
			if (onLastTileChecked != null && onLastTileChecked.getTeam().getName() != "Nature" && onLastTileChecked.getTeam().getName() != searchingHuman.getTeam().getName()) {
				targetSighted = true;
			}
			
		} while (!targetSighted && !openList.isEmpty());

		if (!targetSighted) {
			onLastTileChecked = null;
		} else {
			searchingHuman.getStateStack().clear();
			searchingHuman.getStateStack().push(new Attack((Fighter)searchingHuman));
			
			DijkstraTile lastTileAdded = closedList.get(0).getParent();
			Tile tileOfTarget = closedList.get(0).getMapTile();
			
			boolean temp = true;
			while (temp) {
				searchingHuman.getStateStack().push(new Move(tileOfTarget, lastTileAdded.getMapTile(), searchingHuman));
				if (lastTileAdded.getParent() == null) {
					temp = false;
				} else {
					lastTileAdded = lastTileAdded.getParent();
				}
			}
			searchingHuman.getStateStack().pop();
		}
		
		
		return onLastTileChecked;
	}
	
//		AStarTile lastTileAdded = (AStarTile) closedList.get(0);
//		if (movingHuman.getTarget() != null) { // if we are moving to attack, we
//												// must not add the final tile
//			lastTileAdded = (AStarTile) lastTileAdded.getParent();
//		}
//
//		boolean temp = true;
//		while (temp) {
//			movingHuman.getStateStack().push(new Move(goal, lastTileAdded.getMapTile(), movingHuman));
//			if (lastTileAdded.getParent() == null) {
//				temp = false;
//			} else {
//				lastTileAdded = (AStarTile) lastTileAdded.getParent();
//			}
//		}
//		movingHuman.getStateStack().pop();
	
	// public void findClosest(Tile startLocation, Object objectToFind) {
	// openList.clear();
	// closedList.clear();
	//
	// openList.add(new DijkstraTile(startLocation, null));
	//
	// DijkstraTile currentTile;
	//
	// do {
	// currentTile = chooseLowestF();
	// openList.remove(currentTile);
	// closedList.add(0, currentTile);
	//
	// addValidDijkstraTileToOpenList(currentTile.getMapTile().getMapLocation().x
	// - 1, currentTile.getMapTile().getMapLocation().y, currentTile);
	// addValidDijkstraTileToOpenList(currentTile.getMapTile().getMapLocation().x
	// - 1, currentTile.getMapTile().getMapLocation().y - 1, currentTile);
	// addValidDijkstraTileToOpenList(currentTile.getMapTile().getMapLocation().x
	// - 1, currentTile.getMapTile().getMapLocation().y + 1, currentTile);
	// addValidDijkstraTileToOpenList(currentTile.getMapTile().getMapLocation().x,
	// currentTile.getMapTile().getMapLocation().y - 1, currentTile);
	// addValidDijkstraTileToOpenList(currentTile.getMapTile().getMapLocation().x,
	// currentTile.getMapTile().getMapLocation().y + 1, currentTile);
	// addValidDijkstraTileToOpenList(currentTile.getMapTile().getMapLocation().x
	// + 1, currentTile.getMapTile().getMapLocation().y - 1, currentTile);
	// addValidDijkstraTileToOpenList(currentTile.getMapTile().getMapLocation().x
	// + 1, currentTile.getMapTile().getMapLocation().y, currentTile);
	// addValidDijkstraTileToOpenList(currentTile.getMapTile().getMapLocation().x
	// + 1, currentTile.getMapTile().getMapLocation().y + 1, currentTile);
	// } while (closedList.get(0).mapTile.getOnTile() != objectToFind &&
	// !openList.isEmpty());
	// }

	private static void addValidDijkstraTileToOpenList(int row, int column, DijkstraTile tile, int searchRange) {
		if (row >= 0 && column >= 0 && row < Game.MAP_SIZE && column < Game.MAP_SIZE) { // Tile exists
			DijkstraTile checkTile = getTile(row, column, closedList);
			if (!map[row][column].hasObstacle() && checkTile == null) { // if there is no obstacle on the tile and it's not in the closed list
				checkTile = getTile(row, column, openList);
				if (checkTile != null) { //if it's in the open list
					if (checkTile.getParent().calculateG() > tile.calculateG()) {
						checkTile.setParent(tile);
					}
				} else {
					DijkstraTile newTile = new DijkstraTile(map[row][column], tile);
					if (newTile.calculateG() <= searchRange) {
						openList.add(new DijkstraTile(map[row][column], tile));
					}
				}
			}
		}
	}

	private static void addValidAStarTileToOpenList(int row, int column, DijkstraTile tile) {
		if (row >= 0 && column >= 0 && row < Game.MAP_SIZE && column < Game.MAP_SIZE) { // Tile exists
			DijkstraTile checkTile = getTile(row, column, closedList);
			if (!map[row][column].hasObstacle() && checkTile == null) {
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