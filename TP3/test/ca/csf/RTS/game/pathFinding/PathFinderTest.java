package ca.csf.RTS.game.pathFinding;

import org.jsfml.graphics.Color;

import ca.csf.RTS.game.Game;
import ca.csf.RTS.game.Team;
import ca.csf.RTS.game.entity.Entity;
import ca.csf.RTS.game.entity.controllableEntity.human.Worker;

public class PathFinderTest {
	private Worker worker;
	private Game game;
	public void setUp() {
		game = new Game();
		PathFinder.initialisePathFinder(game.map);
		worker = new Worker(game.map[0][0], new Team("Idiot", Color.BLUE), game);
		game.add((Entity) worker);
	}
}
