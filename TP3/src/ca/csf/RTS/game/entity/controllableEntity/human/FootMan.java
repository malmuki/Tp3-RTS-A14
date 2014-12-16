package ca.csf.RTS.game.entity.controllableEntity.human;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.Texture;

import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.Team;
import ca.csf.RTS.game.entity.Entity;
import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.controllableEntity.Fightable;
import ca.csf.RTS.game.entity.controllableEntity.Fighter;
import ca.csf.RTS.game.entity.state.Alert;
import ca.csf.RTS.game.entity.state.Attack;
import ca.csf.RTS.game.entity.state.Move;
import ca.csf.RTS.game.entity.state.State;
import ca.csf.RTS.game.pathFinding.PathFinder;

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
	private static final int RANGE = 15;
	private static final int DAMAGE = 10;
	
	//private static final float  ATTACK_DELAY = 2f;

//	public float getAttackDelay() {
//		return ATTACK_DELAY;
//	}
	
	public FootMan(Tile originTile, Team team, GameEventHandler game) {
		super(originTile, MAX_HEALTH, team, game);
	}

	@Override
	public void attack() {
		((Fightable) target).loseLife(DAMAGE);
	}

	@Override
	public void order(Entity target) {
		if (target.getTeam().getName() != "Nature" && target.getTeam() != this.team) {
			setTarget(target);
			stateStack.push(new Attack(this));
		} else {
			stateStack.push(new Move(target.getTilesOrigin(), this));
		}
	}

	@Override
	public State getDefaultState() {
		return new Alert(this);
	}

	@Override
	public void doTasks(float deltaTime) {
		if (!stateStack.isEmpty()) {

			switch (stateStack.peek().action(deltaTime)) {

			case notFinished:
			case noTargetSighted:
				break;

			case ended:
				stateStack.pop();

				if (stateStack.isEmpty()) {
					if (target != null) {
						stateStack.push(new Attack(this));
					} else {
						stateStack.push(getDefaultState());
					}
				}
				break;
				
			case blocked:
				stateStack.push(new Attack(this));
				break;
				
			case targetSighted:
				//stateStack.push(new Attack(this));
				break;

			case targetTooFar:
				stateStack.push(new Move(((Entity) this.getTarget()).getTilesOrigin(), this));
				break;

			case dead:
				game.remove(this);
				break;

			default:
				break;
			}
		} else {
			stateStack.push(getDefaultState());
		}
	}

	@Override
	public Entity search() {
		return PathFinder.findClosestEnnemy(this, 24);
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