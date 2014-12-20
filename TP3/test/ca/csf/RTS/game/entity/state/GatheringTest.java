package ca.csf.RTS.game.entity.state;

import static org.junit.Assert.*;

import org.jsfml.graphics.Color;
import org.junit.Before;
import org.junit.Test;

import ca.csf.RTS.game.Game;
import ca.csf.RTS.game.Team;
import ca.csf.RTS.game.entity.controllableEntity.human.Worker;
import ca.csf.RTS.game.entity.ressource.Ressource;
import ca.csf.RTS.game.entity.ressource.Tree;
import ca.csf.RTS.game.pathFinding.DirectionFinder;
import ca.csf.RTS.game.pathFinding.PathFinder;

public class GatheringTest {

	private Worker worker;
	private Gathering gathering;
	private Game game;
 
	@Before
	public void setUp() throws Exception {
		game = new Game();
		DirectionFinder.initialise(game.map);
		PathFinder.initialisePathFinder(game.map);
		worker = new Worker(game.map[0][0], new Team("Idiot", Color.BLUE), game);
		gathering = new Gathering(worker);
		game.add(worker);
	}

	@Test
	public void whenTargetIsNull_thenEnded() {
		assertEquals(StateInteraction.ended, gathering.action(0f));
	}

	@Test
	public void whenDeltaEqual_0f_andIsNotInRange_thenIsNotFinished() {
		worker.setTarget(new Tree(game.map[0][2], new Team(Game.TEAM_NATURE, Color.BLUE), game));
		assertEquals(StateInteraction.targetTooFar, gathering.action(0f));
	}

	@Test
	public void whenDeltaEqual_0f_andIsInRange_thenNoTargetSighted() {
		worker.setTarget(new Tree(game.map[0][1], new Team(Game.TEAM_NATURE, Color.BLUE), game));
		assertEquals(StateInteraction.notFinished, gathering.action(0f));
	}

	@Test
	public void whenCollectingIsOk_andIsInRange_butIsNotDepleted_thenNoTargetSighted() {
		worker.setTarget(new Tree(game.map[0][1], new Team(Game.TEAM_NATURE, Color.BLUE), game));
		assertEquals(StateInteraction.notFinished, gathering.action(0f));
	}

	@Test
	public void whenCollectingIsOk_andIsInRange_butIsDepleted_thenRessourceDepleted() {
		worker.setTarget(new Tree(game.map[0][1], new Team(Game.TEAM_NATURE, Color.BLUE), game));
		for (int i = 0; i < ((Ressource) worker.getTarget()).getRessourceMax() / Gathering.RESSOURCES_PER_COLLECT_TIME - 1; i++) {
			gathering.action(Gathering.MIN_COLLECT_TIME);
		}
		assertEquals(StateInteraction.ressourceDepleted, gathering.action(Gathering.MIN_COLLECT_TIME));
	}
}
