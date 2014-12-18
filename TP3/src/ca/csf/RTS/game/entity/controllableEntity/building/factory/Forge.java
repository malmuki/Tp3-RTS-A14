package ca.csf.RTS.game.entity.controllableEntity.building.factory;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2i;

import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.Team;
import ca.csf.RTS.game.entity.Entity;
import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.state.State;

public class Forge extends Factory {

  private static String TEXTURE_PATH = "./ressource/Soldat.png";
  private static Texture texture;
  private static final int MAX_HEALTH = 1000;
  private static final String NAME = "Forge";
  private static final Vector2i DIMENSION = new Vector2i(5, 7);

  public Forge(Tile originTile, Team team, GameEventHandler game) {
    super(originTile, MAX_HEALTH, team, game, DIMENSION);
    try {
      if (texture == null) {
        texture = new Texture();
        texture.loadFromFile(Paths.get(TEXTURE_PATH));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    sprite.setTexture(texture);
    setSpritePos();
  }

  public void order(Entity onTile) {}

  public void order(Tile target) {}

  @Override
  public void doTasks(float deltaTime) {

  }

  @Override
  public String getName() {
    return NAME;
  }

  @Override
  public boolean spawnNext() {
    return false;
  }

  @Override
  protected State getDefaultState() {
    return null;
  }

  @Override
  protected Trainable getTrainable(int index) {
    return null;
  }
}
