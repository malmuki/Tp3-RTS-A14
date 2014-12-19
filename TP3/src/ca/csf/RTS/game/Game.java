package ca.csf.RTS.game;

import java.util.ArrayList;
import java.util.Random;

import org.jsfml.graphics.Color;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.audio.SoundLoader;
import ca.csf.RTS.game.audio.SoundPlayer;
import ca.csf.RTS.game.entity.Entity;
import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.controllableEntity.Trainee;
import ca.csf.RTS.game.entity.controllableEntity.building.Fondation;
import ca.csf.RTS.game.entity.controllableEntity.building.WatchTower;
import ca.csf.RTS.game.entity.controllableEntity.building.factory.Barrack;
import ca.csf.RTS.game.entity.controllableEntity.building.factory.Factory;
import ca.csf.RTS.game.entity.controllableEntity.building.factory.Forge;
import ca.csf.RTS.game.entity.controllableEntity.building.factory.TownCenter;
import ca.csf.RTS.game.entity.controllableEntity.human.Worker;
import ca.csf.RTS.game.entity.ressource.Stone;
import ca.csf.RTS.game.entity.ressource.Tree;
import ca.csf.RTS.game.pathFinding.DirectionFinder;
import ca.csf.RTS.game.pathFinding.PathFinder;

public class Game implements GameEventHandler {

	public static final int MAP_SIZE = 150;

	public static final String TEAM_PLAYER = "Idiot";
	public static final String TEAM_COMPUTER = "Ennemy";
	public static final String TEAM_NATURE = "Nature";

	private Tile[][] map = new Tile[MAP_SIZE][MAP_SIZE];
	private ArrayList<Entity> entityList;
	private ArrayList<Entity> selectedList;
	private ArrayList<Entity> toBeDeleted;
	private ArrayList<Entity> toBeCreated;

	private Team player;
	private Team computer;
	private Team nature;

	private Worker builder;
	private Vector2i buildingSize;
	private Trainee targetTrainee;

	public Game() {

		SoundLoader.initialize();

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
		DirectionFinder.initialise(map);

		player = new Team(TEAM_PLAYER, Color.YELLOW);
		computer = new Team(TEAM_COMPUTER, Color.RED);
		nature = new Team(TEAM_NATURE, Color.BLACK);

		// stating stuff of the player
		add(new TownCenter(map[2][2], player, this));
		for (int i = 1; i < 12; i += 2) {
			add(new Worker(map[i][12], player, this));
		}

		add(new TownCenter(map[MAP_SIZE - 10][MAP_SIZE - 10], computer, this));
		for (int i = 2; i < 13; i += 2) {
			add(new Worker(map[MAP_SIZE - i][MAP_SIZE - 12], computer, this));
		}

		Random random = new Random();

		placeTree(1000, random);
		placeStone(200, random);

	}

	public void placeTree(int nbTree, Random random) {
		int x = random.nextInt(MAP_SIZE);
		int y = random.nextInt(MAP_SIZE);

		if (nbTree > 0) {
			if (map[x][y].getOnTile() == null) {
				add(new Tree(map[x][y], nature, this));
				placeTree(nbTree - 1, random);
			} else {
				placeTree(nbTree, random);
			}
		}
	}

	public void placeStone(int nbStone, Random random) {
		int x = random.nextInt(MAP_SIZE);
		int y = random.nextInt(MAP_SIZE);

		if (nbStone > 0) {
			if (map[x][y].getOnTile() == null) {
				add(new Stone(map[x][y], nature, this));
				placeStone(nbStone - 1, random);
			} else {
				placeStone(nbStone, random);
			}
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
		if (canPlace(entity)) {
			toBeCreated.add(entity);
			for (int i = entity.getTilesOrigin().getMapLocation().x; i < entity.getTilesOrigin().getMapLocation().x + entity.getDimention().x; i++) {
				for (int j = entity.getTilesOrigin().getMapLocation().y; j < entity.getTilesOrigin().getMapLocation().y + entity.getDimention().y; j++) {
					map[i][j].setOnTile(entity);
				}
			}
		}
	}

	private boolean canPlace(Entity entity) {
		if (entity.getTilesOrigin().getMapLocation().x + entity.getDimention().x >= MAP_SIZE
				|| entity.getTilesOrigin().getMapLocation().y + entity.getDimention().y >= MAP_SIZE) {
			return false;
		}

		for (int i = entity.getTilesOrigin().getMapLocation().x; i < entity.getTilesOrigin().getMapLocation().x + entity.getDimention().x; i++) {
			for (int j = entity.getTilesOrigin().getMapLocation().y; j < entity.getTilesOrigin().getMapLocation().y + entity.getDimention().y; j++) {
				if (map[i][j].getOnTile() != null) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean canPlace(Vector2i pos, Vector2i dim) {
		if (pos.x + dim.x >= MAP_SIZE || pos.y + dim.y >= MAP_SIZE) {
			return false;
		}

		for (int i = pos.x; i < pos.x + dim.x; i++) {
			for (int j = pos.y; j < pos.y + dim.y; j++) {
				if (map[i][j].getOnTile() != null) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void remove(Entity entity) {
		toBeDeleted.add(entity);
		selectedList.remove(entity);
		for (int i = entity.getTilesOrigin().getMapLocation().x; i < entity.getTilesOrigin().getMapLocation().x + entity.getDimention().x; i++) {
			for (int j = entity.getTilesOrigin().getMapLocation().y; j < entity.getTilesOrigin().getMapLocation().y + entity.getDimention().y; j++) {
				map[i][j].setOnTile(null);
			}
		}
	}

	public Team getPlayerTeam() {
		return player;
	}

	public void btnAction(int index, GameController gameController) {
		if (selectedList.isEmpty()) {
			return;
		}
		switch (selectedList.get(0).getName()) {
		case "Forge":
			break;
		case "Barrack":
			((Factory) selectedList.get(0)).addToQueue(index);
			break;
		case "TownCenter":
			((Factory) selectedList.get(0)).addToQueue(index);
			break;
		case "Worker":
			builder = (Worker) selectedList.get(0);
			targetTrainee = builder.getBuildingOrder(index);
			buildingSize = builder.getBuildingSize(targetTrainee);
			gameController.setBuildingColor();
			break;
		default:
			break;
		}
	}

	public void build(Vector2i pos) {
		if (builder.getTeam().substractWood(targetTrainee.woodCost())) {
			if (builder.getTeam().substractStone(targetTrainee.stoneCost())) {
				builder.build(targetTrainee);
				Fondation fondation = null;
				switch (targetTrainee) {
				case BARRACK:
					Barrack barrack = new Barrack(map[pos.x][pos.y], builder.getTeam(), this);
					fondation = new Fondation(barrack);
					break;
				case TOWN_CENTER:
					TownCenter townCenter = new TownCenter(map[pos.x][pos.y], builder.getTeam(), this);
					fondation = new Fondation(townCenter);
					break;
				case FORGE:
					Forge forge = new Forge(map[pos.x][pos.y], builder.getTeam(), this);
					fondation = new Fondation(forge);
					break;
				case WATCH_TOWER:
					WatchTower tower = new WatchTower(map[pos.x][pos.y], builder.getTeam(), this);
					fondation = new Fondation(tower);
					break;
				default:
					break;
				}
				add(fondation);
				builder.setTarget(fondation);
			} else {
				builder.getTeam().addWood(targetTrainee.woodCost());

				SoundPlayer.playSound(2);
			}
		} else {
			SoundPlayer.playSound(3);
		}
	}

	public Vector2i getBuildingSize() {
		return buildingSize;
	}

	public void clearBuilding() {
		buildingSize = null;
		builder = null;
	}

}
