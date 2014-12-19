package ca.csf.RTS.game.pathFinding;

import ca.csf.RTS.game.entity.Entity;
import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.controllableEntity.ControlableEntity;

public class DirectionFinder {

	private static Tile[][] map;

	public static void initialise(Tile[][] map) {
		DirectionFinder.map = map;
	}

	public static RelativePosition getDirection(ControlableEntity source) {
		// left
		if (source.getCenter().x > source.getTarget().getTilesOrigin().getMapLocation().x + source.getTarget().getDimention().x - 1) {
			// top
			if (source.getCenter().y > source.getTarget().getTilesOrigin().getMapLocation().y + source.getTarget().getDimention().y - 1) {
				return RelativePosition.TOP_LEFT;
				// bottom
			} else if (source.getCenter().y < source.getTarget().getTilesOrigin().getMapLocation().y) {
				return RelativePosition.BOTTOM_LEFT;
				// middle
			} else {
				return RelativePosition.LEFT;
			}
			// right
		} else if (source.getCenter().x < source.getTarget().getTilesOrigin().getMapLocation().x) {
			// top
			if (source.getCenter().y > source.getTarget().getTilesOrigin().getMapLocation().y + source.getTarget().getDimention().y - 1) {
				return RelativePosition.TOP_RIGHT;
				// bottom
			} else if (source.getCenter().y < source.getTarget().getTilesOrigin().getMapLocation().y) {
				return RelativePosition.BOTTOM_RIGHT;
				// middle
			} else {
				return RelativePosition.RIGHT;
			}
			// middle
		} else {
			// top
			if (source.getCenter().y > source.getTarget().getTilesOrigin().getMapLocation().y + source.getTarget().getDimention().y - 1) {
				return RelativePosition.TOP;
				// bottom
			} else if (source.getCenter().y < source.getTarget().getTilesOrigin().getMapLocation().y) {
				return RelativePosition.BOTTOM;
				// middle
			} else {
				return null;
			}
		}
	}

	public static Tile getClosestLocation(ControlableEntity source) {
		// determine la case la plus proche de la source par rapport a ou elle est
		Entity target = source.getTarget();

		switch (getDirection(source)) {
		case BOTTOM:
			return map[source.getTilesOrigin().getMapLocation().x][target.getTilesOrigin().getMapLocation().y];
		case BOTTOM_LEFT:
			return map[target.getTilesOrigin().getMapLocation().x + target.getDimention().x - 1][target.getTilesOrigin().getMapLocation().y];
		case BOTTOM_RIGHT:
			return target.getTilesOrigin();
		case LEFT:
			return map[target.getTilesOrigin().getMapLocation().x + target.getDimention().x - 1][source.getTilesOrigin().getMapLocation().y];
		case RIGHT:
			return map[target.getTilesOrigin().getMapLocation().x][source.getTilesOrigin().getMapLocation().y];
		case TOP:
			return map[source.getTilesOrigin().getMapLocation().x][target.getTilesOrigin().getMapLocation().y + target.getDimention().y - 1];
		case TOP_LEFT:
			return map[target.getTilesOrigin().getMapLocation().x + target.getDimention().x - 1][target.getTilesOrigin().getMapLocation().y
					+ target.getDimention().y - 1];
		case TOP_RIGHT:
			return map[target.getTilesOrigin().getMapLocation().x][target.getTilesOrigin().getMapLocation().y + target.getDimention().y - 1];
		default:
			return source.getTilesOrigin();
		}

	}
}
