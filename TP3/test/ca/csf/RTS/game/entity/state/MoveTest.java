package ca.csf.RTS.game.entity.state;

import static org.junit.Assert.assertEquals;

import org.jsfml.graphics.Color;
import org.junit.Before;
import org.junit.Test;

import ca.csf.RTS.game.Game;
import ca.csf.RTS.game.Team;
import ca.csf.RTS.game.entity.controllableEntity.human.Human;
import ca.csf.RTS.game.entity.controllableEntity.human.Worker;
import ca.csf.RTS.game.pathFinding.DirectionFinder;
import ca.csf.RTS.game.pathFinding.PathFinder;

public class MoveTest {

	private Worker worker;
	private Move move;
	private Game game;

	@Before
	public void setUp() throws Exception {
		game = new Game();
		DirectionFinder.initialise(game.map);
		PathFinder.initialisePathFinder(game.map);
		worker = new Worker(game.map[0][0], new Team("Idiot", Color.BLUE), game);
		game.add(worker);
	}

	@Test
	public void whenNextToDestinationTile_andNotingOnTile_andMovingDone_thenEnded() {
		move = new Move(game.map[0][1], worker);
		assertEquals(StateInteraction.ended, move.action(Human.MOVE_DELAY));
	}

	@Test
	public void whenNextToDestinationTile_andNotingOnTile_andMovingNotDone_thenEnded() {
		move = new Move(game.map[0][1], worker);
		assertEquals(StateInteraction.notFinished, move.action(0));
	}

	@Test
	public void whenNextToDestinationTile_andNotNotingOnTile_thenEnded() {
		move = new Move(game.map[0][1], worker);
		game.map[0][1].setOnTile(new Worker(game.map[0][1], new Team("Idiot", Color.BLUE), game));
		assertEquals(StateInteraction.ended, move.action(0));
	}

	@Test
	public void whenNotNextToDestinationTile_andNoNext_theNotFnished() {
		move = new Move(game.map[0][2], worker);
		assertEquals(StateInteraction.notFinished, move.action(0));
	}

	@Test
	public void whenNotNextToDestinationTile_andAsNext_andNextIsEmpty_andMovingNotDone_thenNotFinished() {
		move = new Move(game.map[0][2], worker);
		move.action(Human.MOVE_DELAY);
		assertEquals(StateInteraction.notFinished, move.action(0));
	}

	@Test
	public void whenNotNextToDestinationTile_andAsNext_andNextIsEmpty_andMovingDone_thenNotFinished() {
		move = new Move(game.map[0][2], worker);
		move.action(Human.MOVE_DELAY);
		assertEquals(StateInteraction.ended, worker.getStateStack().pop().action(Human.MOVE_DELAY));
	}

	@Test
	public void whenNotNextToDestinationTile_andAsNext_andNextIsNotEmpty_andIsNotTarget_thenBlocked() {
		move = new Move(game.map[0][4], worker);
		game.add(new Worker(game.map[0][2], new Team("Idiot", Color.BLUE), game));
		worker.setTarget(game.map[0][2].getOnTile());
		move.action(Human.MOVE_DELAY);
		worker.getStateStack().pop().action(Human.MOVE_DELAY);

		assertEquals(StateInteraction.blocked, worker.getStateStack().pop().action(Human.MOVE_DELAY));
	}
}
