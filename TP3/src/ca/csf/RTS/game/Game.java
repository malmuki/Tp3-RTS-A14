package ca.csf.RTS.game;

import java.util.ArrayList;

import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.entity.GameEntity;
import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.controllableEntity.ControlableEntity;
import ca.csf.RTS.game.entity.controllableEntity.human.FootMan;

public class Game {

	public static final int MAP_SIZE = 150;

	private ArrayList<GameEventHandler> gameEventHandler;
	private Tile[][] map = new Tile[MAP_SIZE][MAP_SIZE];
	private ArrayList<GameEntity> entityList;
	private ArrayList<ControlableEntity> selectedList;
	// temporaire
	private FootMan footman1;
	private FootMan footman2;

	public Game() {
		selectedList = new ArrayList<ControlableEntity>();
		gameEventHandler = new ArrayList<GameEventHandler>();
		entityList = new ArrayList<GameEntity>();
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
		ArrayList<Tile> temp = new ArrayList<Tile>();
		temp.add(new Tile(new Vector2i(5, 5)));
		footman1 = new FootMan(temp);
		entityList.add(footman1);
		map[5][5].setOnTile(footman1);
		
		temp.clear();
		temp.add(new Tile(new Vector2i(6, 7)));
		footman2 = new FootMan(temp);
		entityList.add(footman2);
		map[6][7].setOnTile(footman2);
	}

	public ArrayList<GameEntity> getAllEntity() {
		return entityList;
	}

	public ArrayList<ControlableEntity> getAllSelected() {
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
					toHighlight.add((ControlableEntity)map[i][j].getOnTile());//TODO: this will create errors on ressources
				}
			}
		}

		for (ControlableEntity controlableEntity : toHighlight) {
		    controlableEntity.select();
			selectedList.add(controlableEntity);
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
		// pour eviter les ConcurrentModificationExceptions
		for (GameEntity gameEntity : toUnselect) {
			selectedList.remove(gameEntity);
		}
	}

  public void giveOrder(Vector2f mousePos) {
    mousePos = Vector2f.div(mousePos, Tile.TILE_SIZE);
    Tile target = map[(int) mousePos.x][(int) mousePos.y];
    for (ControlableEntity currentSelected : getAllSelected()) {
      if (target.getOnTile() != null) {
        currentSelected.order(target.getOnTile());
      } else {
        currentSelected.order(target);
      }
    }
  }
}