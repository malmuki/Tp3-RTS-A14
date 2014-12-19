package ca.csf.RTS.game.entity.controllableEntity;

import java.util.Stack;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.Team;
import ca.csf.RTS.game.entity.Entity;
import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.state.*;
import ca.csf.RTS.game.pathFinding.DirectionFinder;
import ca.csf.RTS.game.pathFinding.RelativePosition;

public abstract class ControlableEntity extends Entity implements Fightable {

	private final float LIFE_BAR_W = 10;
	private final float LIFE_BAR_H = 4;

	protected final int healthMax;
	protected int healthCurrent;
	protected Stack<State> stateStack;
	protected RectangleShape lifeBar;
	protected RectangleShape lifeBorder;
	protected Entity target;

	public ControlableEntity(Tile originTile, Team team, GameEventHandler game, int healthMax) {
		super(originTile, team, game);
		stateStack = new Stack<State>();
		this.healthMax = healthMax;
		healthCurrent = this.healthMax;
		lifeBar = new RectangleShape(new Vector2f(LIFE_BAR_W, LIFE_BAR_H));
		lifeBar.setFillColor(Color.GREEN);
		lifeBorder = new RectangleShape(new Vector2f(LIFE_BAR_W, LIFE_BAR_H));
		lifeBorder.setOutlineColor(team.getColor());
		lifeBorder.setOutlineThickness(2);
		lifeBorder.setFillColor(Color.TRANSPARENT);
	}
	
	int frame = 0;
	int row = frame*38 + 5;
	int column = 0 + 15;
	int height = 32;
	int width = 28;
	public void setAnimation(String state) {
		switch (state) {
		case "Move":
			switch (DirectionFinder.getDirection(this)) {
			case RIGHT:
				column = 88;
				width = 28;
				break;
			case TOP:
				column = 15;
				width = 28;
				break;
			case LEFT:
				column = 88 + width;
				width = -width;
				break;
			case BOTTOM:
				column = 164;
				width = 28;
				break;
			default:
				break;
			}
			break;
		case "Idle":
			break;
		case "Dead":
			break;
		case "Alert":
			break;
		}
	}

    private Clock animClock = new Clock();
	public void draw(RenderTarget target) {
		//if (!isEmpty()) {
		//	this.setAnimation(this.getStateStack().peek().getStateName());
		//}
		if (this.getName() == "Worker") {
			if (animClock.getElapsedTime().asSeconds() >= 1) {		
					animClock.restart();
					frame++;
							
					if (frame > 4) {
						frame = 0;
					}
						 	
					sprite.setTextureRect(new IntRect(column, row, width, height));
			}
			super.draw(target);
			lifeBar.setPosition((sprite.getTextureRect().width / 3 + 1 + sprite.getPosition().x), sprite.getPosition().y - 6);
			lifeBorder.setPosition((sprite.getTextureRect().width / 3 + 1 + sprite.getPosition().x), sprite.getPosition().y - 6);
			
		} else {
			super.draw(target);
			lifeBar.setPosition((sprite.getTexture().getSize().x / 2 + sprite.getPosition().x), sprite.getPosition().y - 12);
			lifeBorder.setPosition((sprite.getTexture().getSize().x / 2 + sprite.getPosition().x), sprite.getPosition().y - 12);
		}
		
		lifeBar.setSize(new Vector2f((float)healthCurrent / (float)healthMax * LIFE_BAR_W, LIFE_BAR_H));

		if (selected) {
			lifeBorder.setOutlineColor(Color.BLUE);
		} else {
			lifeBorder.setOutlineColor(team.getColor());
		}

		lifeBar.draw(target, RenderStates.DEFAULT);
		lifeBorder.draw(target, RenderStates.DEFAULT);
	}

	public Stack<State> getStateStack() {
		return stateStack;
	}
	
	public boolean isEmpty() {
		if (stateStack.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void loseLife(int damage) {
		healthCurrent -= damage;
		if (healthCurrent <= 0) {
			stateStack.clear();
			stateStack.push(new Dead());
		}
	}

	@Override
	public int getHP() {
		return healthCurrent;
	}

	@Override
	public int getMaxHealth() {
		return healthMax;
	}

	public Entity getTarget() {
		return target;
	}

	public void setTarget(Entity target) {
		this.target = target;
	}

	public abstract Vector2i getCenter();
	
	protected abstract State getDefaultState();

}
