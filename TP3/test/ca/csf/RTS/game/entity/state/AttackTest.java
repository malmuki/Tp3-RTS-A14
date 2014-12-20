package ca.csf.RTS.game.entity.state;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.jsfml.graphics.Color;
import org.junit.Before;
import org.junit.Test;

import ca.csf.RTS.game.Game;
import ca.csf.RTS.game.Team;
import ca.csf.RTS.game.entity.Entity;
import ca.csf.RTS.game.entity.controllableEntity.human.FootMan;
import ca.csf.RTS.game.pathFinding.DirectionFinder;
import ca.csf.RTS.game.pathFinding.PathFinder;

public class AttackTest {

	private FootMan source;
	private Attack attack;
	private Game game;

	@Before
	public void setUp() throws Exception {
		game = new Game();
		DirectionFinder.initialise(game.map);
		PathFinder.initialisePathFinder(game.map);
		source = new FootMan(game.map[0][0], new Team("Idiot", Color.BLUE), game);
		game.add((Entity) source);
		attack = new Attack(source);
	}

	@Test
	public void whenDeltaEqual_0f_andTargetIsNull_thenEnded() {
		FootMan temp = new FootMan(game.map[0][1], new Team("Ennemy", Color.BLUE), game);
		game.add(temp);
		source.setTarget(temp);
		temp.loseLife(temp.getMaxHealth());
		assertEquals(StateInteraction.ended, attack.action(0f));
	}

	@Test
	public void whenDeltaEqual_0f_andTargetIsAt0HP_thenEnded_AndTargetBecomesNull() {
		game.add(new FootMan(game.map[0][1], new Team("Ennemy", Color.BLUE), game));
		assertEquals(StateInteraction.ended, attack.action(0f));
		assertTrue(source.getTarget() == null);
	}

	@Test
	public void whenDeltaEqual_0f_andIsNotInRange_thenTargetTooFar() {
		FootMan temp = new FootMan(game.map[0][2], new Team("Ennemy", Color.BLUE), game);
		game.add(temp);
		source.setTarget(temp);
		assertEquals(StateInteraction.targetTooFar, attack.action(0f));
	}
	@Test
	public void whenDeltaEqual_0_2f_andIsNotInRange_thenTargetTooFar() {
		FootMan temp = new FootMan(game.map[0][2], new Team("Ennemy", Color.BLUE), game);
		game.add(temp);
		source.setTarget(temp);
		assertEquals(StateInteraction.targetTooFar, attack.action(0f));
	}

	@Test
	public void whenDeltaEqual_0_2f__andIsInRange_thenIsNotFinished() {
		FootMan temp = new FootMan(game.map[0][1], new Team("Ennemy", Color.BLUE), game);
		game.add(temp);
		source.setTarget(temp);
		assertEquals(StateInteraction.notFinished, attack.action(0.2f));
	}

}
