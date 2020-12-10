package games.stendhal.client.actions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.client.MockStendhalClient;
import games.stendhal.client.StendhalClient;
import marauroa.common.game.RPAction;

public class JailActionTest {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		StendhalClient.resetClient();
	}


	@Test
	public void testExecute() {
		new MockStendhalClient() {
			@Override
			public void send(final RPAction action) {
				assertEquals("jail", action.get("type"));
				assertEquals("jerry", action.get("target"));
				assertEquals("5", action.get("minutes"));
				assertEquals("stealing", action.get("reason"));
			}
		};
		final JailAction action = new JailAction();
		assertFalse(action.execute(new String[] {"jerry", "5"}, ""));
		assertTrue(action.execute(new String[] {"jerry", "5"}, "stealing"));
	}
	
	@Test
	public void testGetMaximumParameters() {
		final JailAction action = new JailAction();
		assertThat(action.getMaximumParameters(), is(2));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final JailAction action = new JailAction();
		assertThat(action.getMinimumParameters(), is(2));
	}

}
