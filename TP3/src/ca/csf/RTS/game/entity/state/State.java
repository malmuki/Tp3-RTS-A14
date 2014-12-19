package ca.csf.RTS.game.entity.state;

public interface State {

	public StateInteraction action(float deltaTime);

//	public String getStateName();
}