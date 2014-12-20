package ca.csf.RTS.game.entity.state;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class DeadTest {

	private Dead dead;
	
	@Before
	public void setUp() throws Exception {
		dead = new Dead();
	}
	
	@Test
	public void testAction(){
		assertEquals(StateInteraction.dead, dead.action(0));
	}

}
