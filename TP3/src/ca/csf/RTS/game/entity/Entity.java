package ca.csf.RTS.game.entity;

import java.util.ArrayList;

import org.jsfml.system.Vector2i;

import ca.csf.RTS.game.Game;
import ca.csf.RTS.game.entity.state.State;

public abstract class Entity extends GameObject {

  protected ArrayList<Tile> currentTiles;
  protected boolean selected;

  public Entity(ArrayList<Tile> tiles, String name) {
    super(name);
    selected = false;
    this.currentTiles = tiles;
    setSpritePos();
  }

  public ArrayList<Tile> getCurrentTiles() {
    return currentTiles;
  }

  @Override
  protected void setSpritePos() {
    Vector2i mostTopLeftTile = new Vector2i(Game.MAP_SIZE, Game.MAP_SIZE);
    for (Tile tile : currentTiles) {
      if (tile.getMapLocation().x < mostTopLeftTile.x && tile.getMapLocation().y < mostTopLeftTile.y) {
        mostTopLeftTile = tile.getMapLocation();
      }
    }
    sprite.setPosition(mostTopLeftTile.x * Tile.TILE_SIZE, mostTopLeftTile.y * Tile.TILE_SIZE);
  }

  public void select() {
    selected = true;
  }

  public void deSelect() {
    selected = false;
  }

  public abstract void order(Entity onTile);

  public abstract void order(Tile target);

  public abstract void doTasks();
}
