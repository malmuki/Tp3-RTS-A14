package ca.csf.RTS.game.entity.controllableEntity.building.factory;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2i;

import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.Team;
import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.controllableEntity.human.FootMan;
import ca.csf.RTS.game.entity.state.Idle;
import ca.csf.RTS.game.entity.state.State;
import ca.csf.RTS.game.pathFinding.PathFinder;

public class Barrack extends Factory {

  private static String TEXTURE_PATH = "./ressource/barrack.png";
  private static Texture texture;
  private static final int MAX_HEALTH = 1000;
  private static final String NAME = "Barrack";
  private static final Vector2i DIMENSION = new Vector2i(6, 6);

  public Barrack(Tile originTile, Team team, GameEventHandler game, int healthModifier) {
    super(originTile, MAX_HEALTH + healthModifier, team, game, DIMENSION);
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

  @Override
  public String getName() {
    return NAME;
  }

  public State getDefaultState() {
    return new Idle();
  }

  @Override
  protected Trainable getTrainable(int index) {
    switch (index) {
      case 0:
        return Trainable.FOOTMAN;
      default:
        return null;
    }
  }

  @Override
  public boolean spawnNext() {
    Tile tile = PathFinder.findSpawningSpot(this);
    if (tile != null) {
      switch (trainingQueue.get(0)) {
        case FOOTMAN:
          game.add(new FootMan(tile, team, game));
          break;
        default:
          break;
      }

      return true;
    } else {
      return false;
    }
  }
}
