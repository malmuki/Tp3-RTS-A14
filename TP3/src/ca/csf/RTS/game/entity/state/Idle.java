package ca.csf.RTS.game.entity.state;

public class Idle implements State {

	public Idle() {
	}

	@Override
	public StateInteraction action(float deltaTime) {
		return StateInteraction.notFinished;

	}

	@Override
	public String getStateName() {
		return "Idle";
	}
}
