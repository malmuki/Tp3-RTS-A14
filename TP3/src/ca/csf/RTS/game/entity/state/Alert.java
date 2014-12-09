package ca.csf.RTS.game.entity.state;

public class Alert implements State {

	public Alert() {
    // TODO Auto-generated constructor stub
  }

  @Override
	public StateInteraction action() {
	  if (true) {
			return StateInteraction.targetSighted;
	}else {
		return StateInteraction.noTargetSighted;
	}
	
		
		

	}

}
