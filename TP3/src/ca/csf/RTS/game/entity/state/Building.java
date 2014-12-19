package ca.csf.RTS.game.entity.state;

public class Building implements State {

	@Override
	public StateInteraction action(float deltaTime) {
		return StateInteraction.ended;

	}

}
