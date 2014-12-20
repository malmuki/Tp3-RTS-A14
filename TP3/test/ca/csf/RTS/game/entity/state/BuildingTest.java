package ca.csf.RTS.game.entity.state;

import static org.junit.Assert.assertEquals;

import org.jsfml.graphics.Color;
import org.junit.Before;
import org.junit.Test;

import ca.csf.RTS.game.Game;
import ca.csf.RTS.game.Team;
import ca.csf.RTS.game.entity.controllableEntity.Trainee;
import ca.csf.RTS.game.entity.controllableEntity.building.Foundation;
import ca.csf.RTS.game.entity.controllableEntity.building.factory.Barrack;
import ca.csf.RTS.game.entity.controllableEntity.human.Worker;
import ca.csf.RTS.game.pathFinding.DirectionFinder;
import ca.csf.RTS.game.pathFinding.PathFinder;

public class BuildingTest {

	private Worker worker;
	private Building building;
	private Game game;

	@Before
	public void setUp() throws Exception {
		game = new Game();
		DirectionFinder.initialise(game.map);
		PathFinder.initialisePathFinder(game.map);
		worker = new Worker(game.map[0][0], new Team("Idiot", Color.BLUE), game);
		game.add(worker);
		building = new Building(worker, Trainee.BARRACK);
	}

	@Test
	public void whenDeltaEqual_0f_andIsNotInRange_thenTargetTooFar() {
		worker.setTarget(new Foundation(new Barrack(game.map[5][5], new Team("Idiot", Color.BLUE), game)));
		assertEquals(StateInteraction.targetTooFar, building.action(0f));
	}

	@Test
	public void whenDeltaEqual_0f_andIsInRange_thenIsNotFinished() {
		worker.setTarget(new Foundation(new Barrack(game.map[1][1], new Team("Idiot", Color.BLUE), game)));
		assertEquals(StateInteraction.notFinished, building.action(0f));
	}

	@Test
	public void whenBuildingIsDone_andIsInRange_thenNoTargetSighted() {
		worker.setTarget(new Foundation(new Barrack(game.map[1][1], new Team("Idiot", Color.BLUE), game)));
		assertEquals(StateInteraction.ended, building.action(Trainee.BARRACK.time()));
	}

}
