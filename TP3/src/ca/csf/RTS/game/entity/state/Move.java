package ca.csf.RTS.game.entity.state;

import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.controllableEntity.human.Human;

public class Move implements State {

  private Tile finalDestination;
  private Tile next;
  private Human human;

  public Move(Tile target, Human human) {
    finalDestination = target;
    this.human = human;
    next = null;
  }

  public Move(Tile target, Tile nextMove, Human human) {
    finalDestination = target;
    this.human = human;
    next = nextMove;
  }

  @Override
  public void action() {
    // TODO: Pathfinder.findPath(human, target);
    if (human.getCurrentTiles().get(0).getDistance(finalDestination) <= 14) {
      if (finalDestination.getOnTile() == null) {
        //vas-y! fais le pour toi!
        human.move(finalDestination);
      } else {
        human.getStateStack().clear();
        human.getStateStack().add(human.getDefaultState());
      }
      //Déplacement du human
    } else {
//A*
    }
  }

}