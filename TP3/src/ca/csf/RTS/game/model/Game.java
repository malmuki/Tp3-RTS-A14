package ca.csf.RTS.game.model;

import java.util.ArrayList;

import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import ca.csf.RTS.entity.E_Entity;
import ca.csf.RTS.entity.Entity;
import ca.csf.RTS.entity.EntityFactory;
import ca.csf.RTS.eventHandler.GameEventHandler;

public class Game {

	public static final int MAP_SIZE = 150;

	private ArrayList<GameEventHandler> gameEventHandler;
	private Tile[][] map = new Tile[MAP_SIZE][MAP_SIZE];
	private ArrayList<Entity> entityList;

	public Game() {
		gameEventHandler = new ArrayList<GameEventHandler>();
		entityList = new ArrayList<Entity>();
		for (int i = 0; i < MAP_SIZE; i++) {
			for (int j = 0; j < MAP_SIZE; j++) {
				map[i][j] = new Tile(new Vector2i(i, j));
			}
		}
	}

	public void addEventHandler(GameEventHandler handler) {
		gameEventHandler.add(handler);
	}

	public void newGame() {
		entityList.add(EntityFactory.getInstance().getEntity(E_Entity.SOLDAT));
	}

	public ArrayList<Entity> getAllEntity() {
		return entityList;
	}

	public void selectEntity(Vector2f selection1, Vector2f selection2) {
		ArrayList<Entity> toHighlight = new ArrayList<Entity>();

		for (int i = (int) selection1.x / (int) Tile.TILE_SIZE; i < selection2.x
				/ (int) Tile.TILE_SIZE; i++) {
			for (int j = (int) selection1.y / (int) Tile.TILE_SIZE; j < selection2.y
					/ (int) Tile.TILE_SIZE; j++) {
				if (map[i][j].getOnTile() != null) {
					toHighlight.add(map[i][j].getOnTile());
				}
			}
		}

		for (GameEventHandler game : gameEventHandler) {
			game.highlightSelected(toHighlight);
		}
	}
}
