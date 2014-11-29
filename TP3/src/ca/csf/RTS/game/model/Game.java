package ca.csf.RTS.game.model;

import java.util.ArrayList;

import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.entity.GameEntity;
import ca.csf.RTS.game.entity.controllableEntity.human.FootMan;

public class Game {

	public static final int MAP_SIZE = 150;

	private ArrayList<GameEventHandler> gameEventHandler;
	private Tile[][] map = new Tile[MAP_SIZE][MAP_SIZE];
	private ArrayList<GameEntity> entityList;
	private ArrayList<GameEntity> selectedList;
	// temporaire
	private FootMan footman;

	public Game() {
		selectedList = new ArrayList<GameEntity>();
		gameEventHandler = new ArrayList<GameEventHandler>();
		entityList = new ArrayList<GameEntity>();
		for (int i = 0; i < MAP_SIZE; i++) {
			for (int j = 0; j < MAP_SIZE; j++) {
				map[i][j] = new Tile(new Vector2i(i, j));
			}
		}
		ArrayList<Tile> allo = new ArrayList<Tile>();
		allo.add(new Tile(new Vector2i(5, 5)));
		footman = new FootMan(allo);
		entityList.add(footman);
		map[5][5].setOnTile(footman);
	}

	public void addEventHandler(GameEventHandler handler) {
		gameEventHandler.add(handler);
	}

	public void newGame() {

	}

	public ArrayList<GameEntity> getAllEntity() {
		return entityList;
	}

	public void selectEntity(Vector2f selection1, Vector2f selection2) {
		ArrayList<GameEntity> toHighlight = new ArrayList<GameEntity>();

		selection1 = Vector2f.div(selection1, Tile.TILE_SIZE);
		selection2 = Vector2f.div(selection2, Tile.TILE_SIZE);

		for (int i = (int) selection1.x; i < selection2.x; i++) {
			for (int j = (int) selection1.y; j < selection2.y; j++) {
				if (map[i][j].getOnTile() != null) {
					toHighlight.add(map[i][j].getOnTile());
				}
			}
		}

		for (GameEntity gameEntity : toHighlight) {
			gameEntity.select();
			selectedList.add(gameEntity);
		}

		ArrayList<GameEntity> toUnselect = new ArrayList<GameEntity>();
		// to deselect les entity qui ne sont plus selectionner
		for (GameEntity gameEntity : selectedList) {
			boolean isSelected = false;
			for (GameEntity gameEntitySelection : toHighlight) {
				if (gameEntity == gameEntitySelection) {
					isSelected = true;
				}
			}
			if (!isSelected) {
				gameEntity.deSelect();
				toUnselect.add(gameEntity);
			}
		}
		//pour eviter les ConcurrentModificationExceptions
		for (GameEntity gameEntity : toUnselect) {
			selectedList.remove(gameEntity);
		}
	}

}
