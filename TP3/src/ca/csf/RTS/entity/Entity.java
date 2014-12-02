package ca.csf.RTS.entity;

import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;

import ca.csf.RTS.entity.concrete.state.State;
import ca.csf.RTS.game.model.Tile;

public abstract class Entity implements State{
	protected Sprite sprite;
	protected Tile tile;
	protected State state;
	protected Entity target;
	protected boolean selected;
	
	public void draw(RenderTarget target) {
		sprite.draw(target, RenderStates.DEFAULT);
	}
	
	@Override
	public void action(Entity entity){
		state.action(this);
	}
	
	public void setState(State nextState){
		state = nextState;
	}
}
