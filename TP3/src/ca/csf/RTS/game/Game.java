package ca.csf.RTS.game;

import java.util.ArrayList;

import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.entity.Entity;
import ca.csf.RTS.game.entity.Team;
import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.controllableEntity.ControlableEntity;
import ca.csf.RTS.game.entity.controllableEntity.human.FootMan;
import ca.csf.RTS.game.pathFinding.PathFinder;

public class Game implements GameEventHandler {

	public static final int MAP_SIZE = 150;

	private Tile[][] map = new Tile[MAP_SIZE][MAP_SIZE];
	private ArrayList<Entity> entityList;
	private ArrayList<Entity> selectedList;

	// TODO: temporaire, à enlever
	private FootMan footman1;
	private FootMan footman2;

	public Game() {
		selectedList = new ArrayList<Entity>();
		entityList = new ArrayList<Entity>();
		for (int i = 0; i < MAP_SIZE; i++) {
			for (int j = 0; j < MAP_SIZE; j++) {
				map[i][j] = new Tile(new Vector2i(i, j), this);
			}
		}
	}

	public void doTasks() {
		for (Entity object : entityList) {
			object.doTasks();
		}
	}

	public void newGame() {
		PathFinder.setMap(map);

		// TODO: temporary, remove this
		ArrayList<Tile> temp = new ArrayList<Tile>();
		temp.add(new Tile(new Vector2i(5, 5), this));
		footman1 = new FootMan(temp, Team.AI, this);
		entityList.add(footman1);
		map[5][5].setOnTile(footman1);
		footman1.getStateStack().add(footman1.getDefaultState());

		temp.clear();
		temp.add(new Tile(new Vector2i(6, 7), this));
		footman2 = new FootMan(temp, Team.PLAYER, this);
		entityList.add(footman2);
		map[6][7].setOnTile(footman2);
		footman2.getStateStack().add(footman2.getDefaultState());
	}

	public ArrayList<Entity> getAllEntity() {
		return entityList;
	}

	public ArrayList<Entity> getAllSelected() {
		return selectedList;
	}

	public void selectEntity(Vector2f selection1, Vector2f selection2) {
		ArrayList<ControlableEntity> toHighlight = new ArrayList<ControlableEntity>();

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
					toHighlight.add((ControlableEntity) map[i][j].getOnTile());// TODO:
																				// this
																				// will
																				// create
																				// errors
																				// on
																				// ressources
				}
			}
		}

		for (ControlableEntity controlableEntity : toHighlight) {
			controlableEntity.select();
			selectedList.add(controlableEntity);
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
		// TODO: check if not ressource
		for (Entity entity : getAllSelected()) {
			if (target.getOnTile() != null) {
				entity.order(target.getOnTile());
			} else {
				entity.order(target);
			}
		}
	}

	@Override
	public void remove(Entity entity) {
		entityList.remove(entity);
		selectedList.remove(entity);
		for (Tile tile : entity.getCurrentTiles()) {
			tile.setOnTile(null);
		}
	}
}
