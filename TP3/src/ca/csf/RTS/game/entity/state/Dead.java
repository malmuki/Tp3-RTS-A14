package ca.csf.RTS.game.entity.state;

public class Dead implements State {

	@Override
	public StateInteraction action(float deltaTime) {
		
		return StateInteraction.dead;
	}

}
