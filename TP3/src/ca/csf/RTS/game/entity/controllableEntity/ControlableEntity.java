package ca.csf.RTS.game.entity.controllableEntity;

import java.util.Stack;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.system.Vector2f;

import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.Team;
import ca.csf.RTS.game.entity.Entity;
import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.state.Dead;
import ca.csf.RTS.game.entity.state.State;

public abstract class ControlableEntity extends Entity implements Fightable {

	private final float LIFE_BAR_W = 10;
	private final float LIFE_BAR_H = 4;

	protected final int healthMax;
	protected int healthCurrent;
	protected Stack<State> stateStack;
	protected RectangleShape lifeBar;
	protected RectangleShape lifeBorder;
	protected Entity target;

	public ControlableEntity(Tile originTile, int healthMax, Team team, GameEventHandler game) {
		super(originTile, team, game);
		this.healthMax = healthMax;
		healthCurrent = healthMax;
		stateStack = new Stack<State>();

		lifeBar = new RectangleShape(new Vector2f(LIFE_BAR_W, LIFE_BAR_H));
		lifeBar.setFillColor(Color.GREEN);
		lifeBorder = new RectangleShape(new Vector2f(LIFE_BAR_W, LIFE_BAR_H));
		lifeBorder.setOutlineColor(team.getColor());
		lifeBorder.setOutlineThickness(2);
		lifeBorder.setFillColor(Color.TRANSPARENT);
	}

	public void draw(RenderTarget target) {
		super.draw(target);

		lifeBar.setPosition((sprite.getTexture().getSize().x / 2 + sprite.getPosition().x), sprite.getPosition().y - 12);
		lifeBorder.setPosition((sprite.getTexture().getSize().x / 2 + sprite.getPosition().x), sprite.getPosition().y - 12);

		
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
	public int getMaxHealth(){
		return healthMax;
	}

	public Entity getTarget() {
		return target;
	}

	public void setTarget(Entity target) {
		this.target = target;
	}
}