
package ca.csf.RTS.game.entity.state;

public class Idle implements State {

	public Idle() {
  }

  @Override
	public StateInteraction action() {
		return StateInteraction.ended;
		
	}
}
