package ca.csf.RTS.game.entity;

import java.util.ArrayList;

import org.jsfml.system.Vector2i;

import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.Game;

public abstract class Entity extends GameObject {

  protected ArrayList<Tile> currentTiles;
  protected boolean selected;
  protected Team team;
  public Entity(ArrayList<Tile> tiles, Team team, GameEventHandler game) {
    super(game);
    selected = false;
    this.currentTiles = tiles;
    this.team = team;
    sprite.setTexture(texture);
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
    if (sprite.getTexture().getSize().y > Tile.TILE_SIZE) {
		sprite.setPosition(mostTopLeftTile.x * Tile.TILE_SIZE, mostTopLeftTile.y * Tile.TILE_SIZE - sprite.getTexture().getSize().y + Tile.TILE_SIZE);
	}else {
		sprite.setPosition(mostTopLeftTile.x * Tile.TILE_SIZE, mostTopLeftTile.y * Tile.TILE_SIZE);
	}
    
  }

  public void select() {
    selected = true;
  }

  public void deSelect() {
    selected = false;
  }

  public abstract void order(Entity onTile);

  public abstract void order(Tile target);

  public abstract void doTasks(float deltaTime);

public Team getTeam() {
	return team;
}

public abstract boolean isObstacle();
}
