package ca.csf.RTS.game.entity.state;

import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.controllableEntity.human.Human;
import ca.csf.RTS.game.pathFinding.PathFinder;

public class Move implements State {

	private Tile finalDestination;
	private Tile next;
	private Human human;
	private float moveProgression = 0;

	public Move(Tile target, Human human) { // Use this constructor for the initial order giving
		finalDestination = target;
		this.human = human;
		next = null;
	}

	public Move(Tile target, Tile nextMove, Human human) { // Use this
															// constructor to
															// add extra moves
															// in the stack
		finalDestination = target;
		this.human = human;
		next = nextMove;
	}

	@Override
	public StateInteraction action(float deltaTime) {
		if (human.getTilesOrigin().getDistance(finalDestination) <= 1) {
			if (finalDestination.getOnTile() != null) {
				human.moveToTile(human.getTilesOrigin(), 0);
				return StateInteraction.ended;
			} else {
				moveProgression += deltaTime;
				if (human.moveToTile(finalDestination, moveProgression)) {
					return StateInteraction.ended;
				}
				return StateInteraction.notFinished;
			}

		} else {
			if (next == null) { // If there is no next, pathfind to the end

				human.getStateStack().pop();

				PathFinder.findPath(human, finalDestination);
				return StateInteraction.notFinished;
			} else { // else just move to the next
				if (next.getOnTile() == null) {
					moveProgression += deltaTime;

					if (human.moveToTile(next, moveProgression)) {
						return StateInteraction.ended;
					} else {
						return StateInteraction.notFinished;
					}

				} else { // if there is something on the next tile, either repathfind to the end OR repathfind to the target
					moveProgression = 0;
					human.moveToTile(human.getTilesOrigin(), moveProgression);
					human.getStateStack().clear();
					if (next.getOnTile() != human.getTarget()) {
						PathFinder.findPath(human, finalDestination);
						return StateInteraction.notFinished;

					} else {
						return StateInteraction.blocked;
					}
				}
			}
		}
	}

//	@Override
	//public String getStateName() {
	//	return "Move";
//	}
}
