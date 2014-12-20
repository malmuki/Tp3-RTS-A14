package ca.csf.RTS.game.entity.state;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class IdleTest {

	private Idle idle;
	
	@Before
	public void setUp() throws Exception {
		idle = new Idle();
	}
	
	@Test
	public void testAction(){
		assertEquals(StateInteraction.notFinished, idle.action(0));
	}


}
