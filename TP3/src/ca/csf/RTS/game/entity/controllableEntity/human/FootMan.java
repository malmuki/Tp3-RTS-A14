package ca.csf.RTS.game.entity.controllableEntity.human;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Texture;

import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.Game;
import ca.csf.RTS.game.Team;
import ca.csf.RTS.game.audio.SoundPlayer;
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

	private static final String TEXTURE_PATH = "./ressource/footman.png";
	private static Texture texture;

	public static final String NAME = "Footman";

	private final int RANGE;
	private final int DAMAGE;
	private final float ATTACK_DELAY;
	public static final int ENNEMY_SEARCH_RANGE = 65; // this accounts for 10
														// per horizontal move
														// and 14 for diagonal

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
		SoundPlayer.playSound(1, 0);
		sprite.setTexture(texture);
		sprite.setTextureRect(new IntRect(314, 10, 36, 48));
		sprite.setScale(0.555f, 0.416f);
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
	public void order(Tile target) {
		super.order(target);
		SoundPlayer.playSound(1, 3);
	}

	@Override
	public void order(Entity target) {
		if (target.getTeam().getName() != Game.TEAM_NATURE && target.getTeam() != this.team) {
			setTarget(target);
			stateStack.push(new Attack(this));
			SoundPlayer.playSound(1, 4);
		} else {
			stateStack.push(new Move(target.getTilesOrigin(), this));
			SoundPlayer.playSound(1, 3);
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
