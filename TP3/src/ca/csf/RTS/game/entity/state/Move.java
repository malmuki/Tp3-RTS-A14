package ca.csf.RTS.game.entity.state;

import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.controllableEntity.human.Human;
import ca.csf.RTS.game.pathFinding.PathFinder;

public class Move implements State {

	private Tile finalDestination;
	private Tile next;
	private Human human;

	public Move(Tile target, Human human) { // Use this constructor for the
											// initial order giving
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
	public StateInteraction action() {
		if (human.getCurrentTiles().get(0).getDistance(finalDestination) <= 14) {
			if (finalDestination.getOnTile() == null) {
				human.moveToTile(finalDestination);
			}
			return StateInteraction.ended;
		} else {
			if (next == null) { // If there is no next, pathfind to the end
				PathFinder.findPath(human, finalDestination);
				return StateInteraction.notFinished;
			} else { // else just move to the next
				if (next.getOnTile() == null) {
					human.moveToTile(next);
					return StateInteraction.ended;
				} else {
					PathFinder.findPath(human, finalDestination);
					return StateInteraction.notFinished;
				}

				// TODO: test if the next has stuff on it, if so, repathfind...
			}
		}

	}

}
