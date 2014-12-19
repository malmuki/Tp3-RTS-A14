package ca.csf.RTS.game.entity.state;

import static org.junit.Assert.*;

import org.jsfml.graphics.Color;
import org.junit.Before;
import org.junit.Test;

import ca.csf.RTS.game.Game;
import ca.csf.RTS.game.Team;
import ca.csf.RTS.game.entity.controllableEntity.building.factory.Barrack;
import ca.csf.RTS.game.entity.controllableEntity.building.factory.Factory;
import ca.csf.RTS.game.pathFinding.DirectionFinder;
import ca.csf.RTS.game.pathFinding.PathFinder;

public class TrainingTest {
	private Factory factory;
	private Training training;
	private Game game;

	@Before
	public void setUp() throws Exception {
		game = new Game();
		DirectionFinder.initialise(game.map);
		PathFinder.initialisePathFinder(game.map);
		factory = new Barrack(game.map[0][0], new Team("Idiot", Color.BLUE), game);
		training = new Training(factory);
		game.add(factory);
	}

	@Test
	public void whenQueueIsEmpty_thenEnded() {
		assertEquals(StateInteraction.ended, training.action(0f));
	}

	@Test
	public void whenProductionTimeIsNotFinished_andQueueNotEmpty_thenNotFinished() {
		factory.addToQueue(0);
		assertEquals(StateInteraction.notFinished, training.action(0f));
	}

	@Test
	public void whenProductionTimeIsFinished_andQueueNotEmpty_andIsAbleToSpawn_thenNotFinished() {
		factory.addToQueue(0);
		assertEquals(StateInteraction.ended, training.action(factory.getQueue().get(0).time()));
	}

	@Test
	public void whenProductionTimeIsFinished_andQueueNotEmpty_andIsNotAbleToSpawn_thenBlocked() {
		for (int i = 0; i < 28; i++) {
			factory.addToQueue(0);
			training.action(factory.getQueue().get(0).time());
		}
		factory.addToQueue(0);
		assertEquals(StateInteraction.blocked, training.action(factory.getQueue().get(0).time()));
	}
}
