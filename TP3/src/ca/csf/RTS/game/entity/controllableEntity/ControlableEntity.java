package ca.csf.RTS.game.entity.controllableEntity;

import java.util.ArrayList;
import java.util.Stack;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.system.Vector2f;

import ca.csf.RTS.game.entity.Entity;
import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.state.State;

public abstract class ControlableEntity extends Entity implements Fightable {

	protected final int healthMax;
	protected int healthCurrent;
	protected Stack<State> stateStack;
	protected RectangleShape lifeBar;
	protected RectangleShape lifeBorder;

	public ControlableEntity(ArrayList<Tile> tiles, String name, int healthMax) {
		super(tiles, name);
		this.healthMax = healthMax;
		healthCurrent = healthMax;
		stateStack = new Stack<State>();

		lifeBar = new RectangleShape(new Vector2f(25, 10));
		lifeBar.setFillColor(Color.GREEN);
		lifeBorder = new RectangleShape(new Vector2f(25, 10));
		lifeBorder.setOutlineColor(Color.GREEN);
		lifeBorder.setOutlineThickness(2);
		lifeBorder.setFillColor(Color.TRANSPARENT);
	}

	public void draw(RenderTarget target) {
		super.draw(target);

		lifeBar.setPosition(
				(sprite.getTexture().getSize().x / 2 + sprite.getPosition().x),
				sprite.getPosition().y - 12);
		lifeBorder.setPosition(
				(sprite.getTexture().getSize().x / 2 + sprite.getPosition().x),
				sprite.getPosition().y - 12);

		lifeBar.setSize(new Vector2f(healthCurrent / healthMax * 25, 10));

		if (selected) {
			lifeBorder.setOutlineColor(Color.BLUE);
		} else {
			lifeBorder.setOutlineColor(Color.GREEN);
		}
		lifeBar.draw(target, RenderStates.DEFAULT);
		lifeBorder.draw(target, RenderStates.DEFAULT);
	}
	
	public void doTasks() {
	  if (!stateStack.isEmpty()) {
        stateStack.pop().action();
      } 
	}
	
	public Stack<State> getStateStack() {
	  return stateStack;
	}
}