package ca.csf.RTS.game.entity.controllableEntity.human;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.Texture;

import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.Team;
import ca.csf.RTS.game.entity.Entity;
import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.state.Gathering;
import ca.csf.RTS.game.entity.state.Idle;
import ca.csf.RTS.game.entity.state.Move;
import ca.csf.RTS.game.entity.state.State;
import ca.csf.RTS.game.pathFinding.PathFinder;

public class Worker extends Human {

  private static String TEXTURE_PATH = "./ressource/worker.gif";
  private Texture texture;
  private static final int MAX_HEALTH = 100;
  private static final String NAME = "Worker";
  private static final int RESSOURCE_SEARCH_RANGE = 35; // this accounts for 10 per horizontal move and 14 for diagonal

  public Worker(Tile originTile, Team team, GameEventHandler game) {
    super(originTile, MAX_HEALTH, team, game);
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
  public State getDefaultState() {
    return new Idle();
  }

  @Override
  public void order(Entity target) {
    stateStack.clear();
    if (target.getTeam().getName() == "Nature") {
      setTarget(target);
      stateStack.push(new Gathering(this));
    } else {
      stateStack.push(new Move(target.getTilesOrigin(), this));
    }
  }

  @Override
  public void doTasks(float deltaTime) {
    if (!stateStack.isEmpty()) {
      switch (stateStack.peek().action(deltaTime)) {
        case ended:
          stateStack.pop();
          if (stateStack.isEmpty()) {
            if (target != null) {
              stateStack.push(new Gathering(this));
            } else {
              stateStack.push(getDefaultState());
            }
          }

          break;
        case targetTooFar:
          stateStack.push(new Move(target.getTilesOrigin(), this));
          break;
        case targetUnreachable:

          break;
        case ressourceDepleted:
          stateStack.pop();
          setTarget(search());
          break;
        case dead:
          game.remove(this);
          break;
        default:
          break;
      }
    }
  }

  @Override
  public Entity search() {
    return PathFinder.findClosestRessource(this, RESSOURCE_SEARCH_RANGE);
  }

  @Override
  public String getName() {
    return NAME;
  }
}
