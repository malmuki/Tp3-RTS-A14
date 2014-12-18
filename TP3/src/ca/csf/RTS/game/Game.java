package ca.csf.RTS.game;

import java.util.ArrayList;

import org.jsfml.graphics.Color;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.entity.Entity;
import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.controllableEntity.building.WatchTower;
import ca.csf.RTS.game.entity.controllableEntity.building.factory.Barrack;
import ca.csf.RTS.game.entity.controllableEntity.building.factory.TownCenter;
import ca.csf.RTS.game.entity.controllableEntity.human.FootMan;
import ca.csf.RTS.game.entity.controllableEntity.human.Worker;
import ca.csf.RTS.game.entity.ressource.Tree;
import ca.csf.RTS.game.entity.state.Alert;
import ca.csf.RTS.game.pathFinding.PathFinder;

public class Game implements GameEventHandler {

	public static final int MAP_SIZE = 150;

	private Tile[][] map = new Tile[MAP_SIZE][MAP_SIZE];
	private ArrayList<Entity> entityList;
	private ArrayList<Entity> selectedList;
	private ArrayList<Entity> toBeDeleted;
	private ArrayList<Entity> toBeCreated;

	private Team player;
	private Team computer;
	private Team nature;

	// TEST: temporaire, à enlever
	private FootMan footman1;
	private FootMan footman2;
	private Tree tree;
	private Worker worker;
	private WatchTower watchtower;
	private Barrack barrack;
	private TownCenter towncenter;

	public Game() {
		selectedList = new ArrayList<Entity>();
		entityList = new ArrayList<Entity>();
		toBeDeleted = new ArrayList<Entity>();
		toBeCreated = new ArrayList<Entity>();
		for (int i = 0; i < MAP_SIZE; i++) {
			for (int j = 0; j < MAP_SIZE; j++) {
				map[i][j] = new Tile(new Vector2i(i, j), this);
			}
		}
	}

	public void doTasks(float deltaTime) {
		for (Entity entity : entityList) {
			entity.doTasks(deltaTime);
		}
		for (Entity entity : toBeDeleted) {
			entityList.remove(entity);
		}
		for (Entity entity : toBeCreated) {
			entityList.add(entity);
		}
		toBeCreated.clear();
		toBeDeleted.clear();
	}

	public void newGame() {
		PathFinder.initialisePathFinder(map);

		player = new Team("Idiot", Color.YELLOW);
		computer = new Team("Ennemy", Color.RED);
		nature = new Team("Nature", Color.BLACK);

		// TEST: temporary, remove this
		footman1 = new FootMan(map[20][20], computer, this);
		add(footman1);
		footman1.getStateStack().push(footman1.getDefaultState());

		footman2 = new FootMan(map[6][7], player, this);
		add(footman2);
		footman2.getStateStack().push(footman2.getDefaultState());
		
		add(new FootMan(map[7][7], computer, this));
		add(new FootMan(map[8][7], computer, this));
		add(new FootMan(map[9][7], computer, this));
		add(new FootMan(map[10][7], computer, this));
		
		worker = new Worker(map[20][10], player, this);
		add(worker);
		worker.getStateStack().push(worker.getDefaultState());

		watchtower = new WatchTower(map[9][9], player, this);
		add(watchtower);
		watchtower.getStateStack().push(new Alert(watchtower));

		barrack = new Barrack(map[4][22], player, this, 0);
		add(barrack);

		tree = new Tree(map[8][8], nature, this);
		add(tree);
		
		Tree tree2 = new Tree(map[8][6], nature, this);
		add(tree2);

		towncenter = new TownCenter(map[40][20], player, this);
		add(towncenter);
	}

	public void allo() {
		if (selectedList.get(0).getName() == "Barrack") {
			((Barrack) selectedList.get(0)).addToQueue(0);
		}else {
			((TownCenter) selectedList.get(0)).addToQueue(0);
		}
		
	}

	public ArrayList<Entity> getAllEntity() {
		return entityList;
	}

	public ArrayList<Entity> getAllSelected() {
		return selectedList;
	}

	public void selectEntity(Vector2f selection1, Vector2f selection2) {
		ArrayList<Entity> toHighlight = new ArrayList<Entity>();

		selection1 = Vector2f.div(selection1, Tile.TILE_SIZE);
		selection2 = Vector2f.div(selection2, Tile.TILE_SIZE);

		if (selection1.x > selection2.x) {
			Vector2f buffer = selection1;
			selection1 = new Vector2f(selection2.x, selection1.y);
			selection2 = new Vector2f(buffer.x, selection2.y);
		}
		if (selection1.y > selection2.y) {
			Vector2f buffer = selection1;
			selection1 = new Vector2f(selection1.x, selection2.y);
			selection2 = new Vector2f(selection2.x, buffer.y);
		}

		for (int i = (int) selection1.x; i < selection2.x; i++) {
			for (int j = (int) selection1.y; j < selection2.y; j++) {
				if (map[i][j].getOnTile() != null) {
					toHighlight.add(map[i][j].getOnTile());
				}
			}
		}

		for (Entity entity : toHighlight) {
			entity.select();
			selectedList.add(entity);
		}

		ArrayList<Entity> toUnselect = new ArrayList<Entity>();
		// to deselect les entity qui ne sont plus selectionner
		for (Entity entity : selectedList) {
			boolean isSelected = false;
			for (Entity gameEntitySelection : toHighlight) {
				if (entity == gameEntitySelection) {
					isSelected = true;
				}
			}
			if (!isSelected) {
				entity.deSelect();
				toUnselect.add(entity);
			}
		}
		// pour eviter les ConcurrentModificationExceptions
		for (Entity entity : toUnselect) {
			selectedList.remove(entity);
		}
	}

	public void giveOrder(Vector2f mousePos) {
		mousePos = Vector2f.div(mousePos, Tile.TILE_SIZE);
		Tile target = map[(int) mousePos.x][(int) mousePos.y];
		for (Entity entity : getAllSelected()) {
			if (target.getOnTile() != null) {
				entity.order(target.getOnTile());
			} else {
				entity.order(target);
			}
		}
	}

	@Override
	public void add(Entity entity) {
		toBeCreated.add(entity);
		for (int i = entity.getTilesOrigin().getMapLocation().x; i < entity.getTilesOrigin().getMapLocation().x + entity.getDimention().x; i++) {
			for (int j = entity.getTilesOrigin().getMapLocation().y; j < entity.getTilesOrigin().getMapLocation().y + entity.getDimention().y; j++) {
				map[i][j].setOnTile(entity);
			}
		}
	}

	@Override
	public void remove(Entity entity) {
		toBeDeleted.add(entity);
		selectedList.remove(entity);
		entity.getTeam().removeUnit(entity);

		for (int i = entity.getTilesOrigin().getMapLocation().x; i < entity.getTilesOrigin().getMapLocation().x + entity.getDimention().x; i++) {
			for (int j = entity.getTilesOrigin().getMapLocation().y; j < entity.getTilesOrigin().getMapLocation().y + entity.getDimention().y; j++) {
				map[i][j].setOnTile(null);
			}
		}
	}

	public Team getPlayerTeam() {
		return player;
	}
}