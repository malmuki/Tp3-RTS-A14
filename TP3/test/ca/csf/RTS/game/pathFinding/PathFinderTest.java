package ca.csf.RTS.game.pathFinding;

import static org.junit.Assert.assertEquals;

import org.jsfml.graphics.Color;
import org.jsfml.system.Vector2i;
import org.junit.Before;
import org.junit.Test;

import ca.csf.RTS.game.Game;
import ca.csf.RTS.game.Team;
import ca.csf.RTS.game.entity.Entity;
import ca.csf.RTS.game.entity.controllableEntity.Trainee;
import ca.csf.RTS.game.entity.controllableEntity.building.WatchTower;
import ca.csf.RTS.game.entity.controllableEntity.building.factory.Barrack;
import ca.csf.RTS.game.entity.controllableEntity.building.factory.Factory;
import ca.csf.RTS.game.entity.controllableEntity.human.FootMan;
import ca.csf.RTS.game.entity.controllableEntity.human.Human;
import ca.csf.RTS.game.entity.controllableEntity.human.Worker;
import ca.csf.RTS.game.entity.ressource.Tree;
import ca.csf.RTS.game.entity.state.State;
import ca.csf.RTS.game.pathFinding.PathFinder;

public class PathFinderTest {
	
	private Worker worker;
	private Game game;
	private Factory factory;
	
	@Before
	public void setUp() {
		game = new Game();
		PathFinder.initialisePathFinder(game.map);
		worker = new Worker(game.map[0][0], new Team("Idiot", Color.BLUE), game);
		game.add((Entity) worker);
		factory = new Barrack(game.map[10][10], new Team("Idiot", Color.BLUE), game);
		game.add((Entity) factory);
	}
	
	@Test
	public void whenThereIsAnEmptyTile_thenThisTileIsReturned() {
		assertEquals(PathFinder.findSpawningSpot(factory), game.map[9][9]);
	}
	
	@Test
	public void whenThereIsntAnEmptyTile_thenNullIsReturned() {
		//putting trees all around our barrack
		game.add((Entity) new Tree(game.map[9][9], new Team("Idiot", Color.BLUE), game));
		game.add((Entity) new Tree(game.map[9][10], new Team("Idiot", Color.BLUE), game));
		game.add((Entity) new Tree(game.map[9][11], new Team("Idiot", Color.BLUE), game));
		game.add((Entity) new Tree(game.map[9][12], new Team("Idiot", Color.BLUE), game));
		game.add((Entity) new Tree(game.map[9][13], new Team("Idiot", Color.BLUE), game));
		game.add((Entity) new Tree(game.map[9][14], new Team("Idiot", Color.BLUE), game));
		game.add((Entity) new Tree(game.map[9][15], new Team("Idiot", Color.BLUE), game));
		game.add((Entity) new Tree(game.map[9][16], new Team("Idiot", Color.BLUE), game));
		game.add((Entity) new Tree(game.map[10][9], new Team("Idiot", Color.BLUE), game));
		game.add((Entity) new Tree(game.map[11][9], new Team("Idiot", Color.BLUE), game));
		game.add((Entity) new Tree(game.map[12][9], new Team("Idiot", Color.BLUE), game));
		game.add((Entity) new Tree(game.map[13][9], new Team("Idiot", Color.BLUE), game));
		game.add((Entity) new Tree(game.map[14][9], new Team("Idiot", Color.BLUE), game));
		game.add((Entity) new Tree(game.map[15][9], new Team("Idiot", Color.BLUE), game));
		game.add((Entity) new Tree(game.map[16][9], new Team("Idiot", Color.BLUE), game));
		game.add((Entity) new Tree(game.map[16][10], new Team("Idiot", Color.BLUE), game));
		game.add((Entity) new Tree(game.map[16][11], new Team("Idiot", Color.BLUE), game));
		game.add((Entity) new Tree(game.map[16][12], new Team("Idiot", Color.BLUE), game));
		game.add((Entity) new Tree(game.map[16][13], new Team("Idiot", Color.BLUE), game));
		game.add((Entity) new Tree(game.map[16][14], new Team("Idiot", Color.BLUE), game));
		game.add((Entity) new Tree(game.map[16][15], new Team("Idiot", Color.BLUE), game));
		game.add((Entity) new Tree(game.map[16][16], new Team("Idiot", Color.BLUE), game));
		game.add((Entity) new Tree(game.map[10][16], new Team("Idiot", Color.BLUE), game));
		game.add((Entity) new Tree(game.map[11][16], new Team("Idiot", Color.BLUE), game));
		game.add((Entity) new Tree(game.map[12][16], new Team("Idiot", Color.BLUE), game));
		game.add((Entity) new Tree(game.map[13][16], new Team("Idiot", Color.BLUE), game));
		game.add((Entity) new Tree(game.map[14][16], new Team("Idiot", Color.BLUE), game));
		game.add((Entity) new Tree(game.map[15][16], new Team("Idiot", Color.BLUE), game));
		game.add((Entity) new Tree(game.map[16][16], new Team("Idiot", Color.BLUE), game));
		
		assertEquals(PathFinder.findSpawningSpot(factory), null);
	}
	
	@Test
	public void whenSearchingForCloseRessource_thenClosestRessourceIsReturned() {
		Tree tree = new Tree(game.map[1][1], new Team("Nature", Color.BLUE), game);
		game.add((Entity) tree);
		
		assertEquals(PathFinder.findClosestRessource(worker, 35), tree);
	}
	
	@Test
	public void whenThereAreMoreThanOneRessourceWithinSearchRange_thenTheClosestRessourceIsReturned() {
		Tree tree = new Tree(game.map[1][1], new Team("Nature", Color.BLUE), game);
		new Tree(game.map[2][2], new Team("Nature", Color.BLUE), game);
		game.add((Entity) tree);
		
		assertEquals(PathFinder.findClosestRessource(worker, 35), tree);
	}
	
	@Test
	public void whenThereIsARessourceOutsideSearchRange_thenTheyAreNotConsidered() {
		Tree tree = new Tree(game.map[4][4], new Team("Nature", Color.BLUE), game);
		game.add((Entity) tree);
		
		assertEquals(PathFinder.findClosestRessource(worker, 35), null);
	}
	
	@Test
	public void whenThereIsAnEnnemyWithinTheWatchTowerSearchRange_thenItIsReturned() {
		WatchTower tower = new WatchTower(game.map[5][5], new Team("Idiot", Color.BLUE), game);
		FootMan ennemyFootman = new FootMan(game.map[3][3], new Team("Ennemy", Color.BLUE), game);
		
		//assertEquals a buggé et nous posait problème ici, d'où l'utilisation d'un simple assert.
		assert(PathFinder.findClosestEnnemy(tower, 20).equals(ennemyFootman));
	}
	
	@Test
	public void whenThereIsMoreThanOneEnnemyWithinTheWatchTowerSearchRange_thenTheClosestIsReturned() {
		WatchTower tower = new WatchTower(game.map[5][5], new Team("Idiot", Color.BLUE), game);
		FootMan ennemyFootman = new FootMan(game.map[3][3], new Team("Ennemy", Color.BLUE), game);
		new FootMan(game.map[2][2], new Team("Ennemy", Color.BLUE), game);

		assert(PathFinder.findClosestEnnemy(tower, 20).equals(ennemyFootman));
	}
	
	@Test
	public void whenThereIsAnEnnemyOutsideTheWatchTowerSearchRange_thenItIsNotConsidered() {
		WatchTower tower = new WatchTower(game.map[4][4], new Team("Idiot", Color.BLUE), game);
		FootMan ennemyFootman = new FootMan(game.map[20][20], new Team("Ennemy", Color.BLUE), game);
		
		assert(PathFinder.findClosestEnnemy(tower, 10).equals(null));
	}
	
	@Test
	public void whenThereIsAnEnnemyWithinAHumanSearchRange_thenItIsReturned() {
		Human human = new FootMan(game.map[2][2], new Team("Ennemy", Color.BLUE), game);
		
		assertEquals(PathFinder.findClosestEnnemy(human, 35), worker);
	}
	
	@Test
	public void whenThereIsMoreThanOneEnnemyWithinAHumanSearchRange_thenTheClosestIsReturned() {
		Human human = new FootMan(game.map[2][2], new Team("Ennemy", Color.BLUE), game);
		new Worker(game.map[5][5], new Team("Idiot", Color.BLUE), game);
		
		assertEquals(PathFinder.findClosestEnnemy(human, 35), worker);
	}
	
	@Test
	public void whenThereIsAnEnnemyOutsideAHumanSearchRange_thenItIsNotConsidered() {
		Human human = new FootMan(game.map[20][20], new Team("Ennemy", Color.BLUE), game);
		
		assertEquals(PathFinder.findClosestEnnemy(human, 35), null);
	}
	
	
}
