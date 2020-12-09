package games.stendhal.client.actions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.client.MockStendhalClient;
import games.stendhal.client.StendhalClient;
import marauroa.common.game.RPAction;

public class BanActionTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		StendhalClient.resetClient();
	}
	
	
	
	@Test
	public void testAction() {
		new MockStendhalClient() {
			@Override
			public void send(final RPAction action) {
				assertEquals("ban", action.get("type"));
				assertEquals("usernamex", action.get("target"));
				assertEquals("3", action.get("hours"));
				assertEquals("some notes", action.get("reason"));
			}
		};
		final BanAction action = new BanAction();
		assertTrue(action.execute(new String[] {"usernamex", "3"}, "some notes" ));
		
	}
	
	
	
	
	/**
	 * Test for getMaximumParameters
	 */
	@Test
	public void testGetMaximumParameters() {
		final BanAction action = new BanAction();
		assertThat(action.getMaximumParameters(), is(2));
	}
	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final BanAction action = new BanAction();
		assertThat(action.getMinimumParameters(), is(2));
	}
	
}
