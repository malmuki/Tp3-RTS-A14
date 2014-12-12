package ca.csf.RTS.game.entity.controllableEntity.human;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.jsfml.graphics.Texture;

import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.entity.Entity;
import ca.csf.RTS.game.entity.Team;
import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.controllableEntity.Fightable;
import ca.csf.RTS.game.entity.controllableEntity.Fighter;
import ca.csf.RTS.game.entity.state.Alert;
import ca.csf.RTS.game.entity.state.Attack;
import ca.csf.RTS.game.entity.state.Move;
import ca.csf.RTS.game.entity.state.State;

public class FootMan extends Human implements Fighter {

	static {
		try {
			texture = new Texture();
			texture.loadFromFile(Paths.get("./ressource/soldat.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static final int MAX_HEALTH = 100;
	private static final String NAME = "Footman";
	private static final int RANGE = 14;
	private static final int DAMAGE = 10;

	public FootMan(ArrayList<Tile> tiles, Team team, GameEventHandler game) {
		super(tiles, MAX_HEALTH, team, game);
		//stateStack.push(getDefaultState());
	}

	@Override
	public void attack(Fightable target) {
		target.loseLife(DAMAGE);
	}

	@Override
	public void order(Entity target) {
		if (target.getTeam() != Team.NATURE && target.getTeam() != this.team) {
			stateStack.push(new Attack((Fightable) target, this));
		}else {
			stateStack.push(new Move(target.getCurrentTiles().get(0), this));
		}
	}

	@Override
	public State getDefaultState() {
		return new Alert(this);
	}

	@Override
	public void doTasks() {
		if (!stateStack.isEmpty()) {
			switch (stateStack.peek().action()) {
			case notFinished:
			case noTargetSighted:
				break;
			case ended:
				stateStack.pop();
				if (stateStack.isEmpty()) {
					stateStack.push(getDefaultState());
				}
				break;
			case targetSighted:
				stateStack.push(new Attack(
						((Fightable) ((Alert) stateStack.peek())
								.getFutureTarget()), this));
				break;
			case targetTooFar:
				Tile temp = ((Entity) ((Attack) stateStack.peek()).getTarget())
						.getCurrentTiles().get(0);
				stateStack.push(new Move(temp, this));
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
		// TODO: dijktras
		return null;
	}

	@Override
	public int getRange() {
		return RANGE;
	}

	@Override
	public int getDamage() {
		return DAMAGE;
	}

	@Override
	public String getName() {
		return NAME;
	}
}
