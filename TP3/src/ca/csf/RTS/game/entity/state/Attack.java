package ca.csf.RTS.game.entity.state;

import org.jsfml.system.Vector2i;

import ca.csf.RTS.game.entity.Entity;
import ca.csf.RTS.game.entity.controllableEntity.ControlableEntity;
import ca.csf.RTS.game.entity.controllableEntity.Fighter;

public class Attack implements State {

	private enum RelativePosition {
		TOP_LEFT, TOP, TOP_RIGHT, LEFT, RIGHT, BOTTOM_LEFT, BOTTOM, BOTTOM_RIGHT
	}

	private Fighter source;
	private float attackProgression = 0.0f;

	public Attack(Fighter source) {
		this.source = source;
	}

	@Override
	public StateInteraction action(float deltaTime) {

		if (source.getTarget() != null) {
			if (((ControlableEntity) source.getTarget()).getHP() <= 0) {
				((ControlableEntity) source).setTarget(null);
				return StateInteraction.ended;
			}

			Vector2i closestLocation;
			ControlableEntity source = (ControlableEntity) this.source;
			Entity target = source.getTarget();

			// determine la case la plus proche de la source par rapport a ou elle est
			switch (getDirection()) {
			case BOTTOM:
				closestLocation = new Vector2i(source.getTilesOrigin().getMapLocation().x, target.getTilesOrigin().getMapLocation().y);
				break;
			case BOTTOM_LEFT:
				closestLocation = new Vector2i(target.getTilesOrigin().getMapLocation().x + target.getDimention().x - 1, target.getTilesOrigin()
						.getMapLocation().y);
				break;
			case BOTTOM_RIGHT:
				closestLocation = target.getTilesOrigin().getMapLocation();
				break;
			case LEFT:
				closestLocation = new Vector2i(target.getTilesOrigin().getMapLocation().x + target.getDimention().x - 1, source.getTilesOrigin()
						.getMapLocation().y);
				break;
			case RIGHT:
				closestLocation = new Vector2i(target.getTilesOrigin().getMapLocation().x, source.getTilesOrigin().getMapLocation().y);
				break;
			case TOP:
				closestLocation = new Vector2i(source.getTilesOrigin().getMapLocation().x, target.getTilesOrigin().getMapLocation().y + target.getDimention().y
						- 1);
				break;
			case TOP_LEFT:
				closestLocation = new Vector2i(target.getTilesOrigin().getMapLocation().x + target.getDimention().x - 1, target.getTilesOrigin()
						.getMapLocation().y + target.getDimention().y - 1);
				break;
			case TOP_RIGHT:
				closestLocation = new Vector2i(target.getTilesOrigin().getMapLocation().x, target.getTilesOrigin().getMapLocation().y + target.getDimention().y
						- 1);
				break;
			default:
				closestLocation = source.getTilesOrigin().getMapLocation();
				break;
			}

			if (source.getTilesOrigin().getDistance(closestLocation) <= this.source.getRange()) {
				attackProgression += deltaTime;

				if (attackProgression > 0.5f) {
					this.source.attack();
					attackProgression = 0.0f;
				}

				return StateInteraction.notFinished;
			} else {

				return StateInteraction.targetTooFar;
			}
		}
		return StateInteraction.ended;
	}

	private RelativePosition getDirection() {
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
}
