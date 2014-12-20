package ca.csf.RTS.game.entity.state;

import static org.junit.Assert.*;

import org.jsfml.graphics.Color;
import org.junit.*;

import ca.csf.RTS.game.Game;
import ca.csf.RTS.game.Team;
import ca.csf.RTS.game.entity.Entity;
import ca.csf.RTS.game.entity.controllableEntity.human.FootMan;
import ca.csf.RTS.game.pathFinding.PathFinder;

public class AlertTest {

	private FootMan watcher;
	private Alert alert;
	private Game game;

	@Before
	public void setUp() throws Exception {
		game = new Game();
		PathFinder.initialisePathFinder(game.map);
		watcher = new FootMan(game.map[0][0], new Team("Idiot", Color.BLUE), game);
		game.add((Entity) watcher);
		alert = new Alert(watcher);
	} 

	@Test
	public void whenDeltaEqual_0f_andNoTargetFound_thenIsNotFinished() {
		assertEquals(StateInteraction.notFinished, alert.action(0f));
	}
 
	@Test
	public void whenDeltaEqual_0f_andTargetShouldBeFound_thenIsNotFinished() {
		game.add(new FootMan(game.map[0][1], new Team("Ennemy", Color.BLUE), game));
		assertEquals(StateInteraction.notFinished, alert.action(0f));
	}

	@Test
	public void whenDeltaEqual_0_2f_andNoTargetFound_thenNoTargetSighted() {
		assertEquals(StateInteraction.noTargetSighted, alert.action(0.2f));
	}

	@Test
	public void whenDeltaEqual_0_2f_andTargetShouldBeFound_thenIsNotFinished() {
		game.add(new FootMan(game.map[0][1], new Team("Ennemy", Color.BLUE), game));
		assertEquals(StateInteraction.targetSighted, alert.action(0.2f));
	}
}
