package ca.csf.RTS.entity.concrete.state;

import ca.csf.RTS.entity.Entity;

public interface State {
	
	public void action(Entity entity);
}