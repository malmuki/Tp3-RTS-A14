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

	private static String TEXTURE_PATH = "./ressource/Soldat.png";
	private static Texture texture;
	private static final String NAME = "Footman";
	private final int RANGE;
	private final int DAMAGE;
	private final float ATTACK_DELAY;
	private static final int ENNEMY_SEARCH_RANGE = 35; // this accounts for 10 per horizontal move and 14 for diagonal

	public FootMan(Tile originTile, Team team, GameEventHandler game) {
		super(originTile, team, game, team.getFootManModel().getHealthMax());
		RANGE = team.getFootManModel().getRange();
		DAMAGE = team.getFootManModel().getDamage();
		ATTACK_DELAY = team.getFootManModel().getAttackDelay();
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
	public float getAttackDelay() {
		return ATTACK_DELAY;
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
		return PathFinder.findClosestEnnemy(this, ENNEMY_SEARCH_RANGE);
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
