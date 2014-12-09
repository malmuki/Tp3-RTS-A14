package ca.csf.RTS.game.entity.state;

public class Dead implements State {

	@Override
	public StateInteraction action() {
		
		return StateInteraction.ended;
	}

}
