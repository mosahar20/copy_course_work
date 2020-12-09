package games.stendhal.client.actions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.client.MockStendhalClient;
import games.stendhal.client.StendhalClient;
import marauroa.common.game.RPAction;

public class TeleportActionTest {
	
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
				assertEquals("teleport", action.get("type"));
				assertEquals("jerry", action.get("target"));
				assertEquals("0_deniran_river_se", action.get("zone"));
				assertEquals("10", action.get("x"));
				assertEquals("10", action.get("y"));
			}
		};
		final TeleportAction action = new TeleportAction();
		assertTrue(action.execute(new String[] {"jerry", "0_deniran_river_se","10","10"}, null));
	}

	@Test
	public void testGetMaximumParameters() {
		final TeleportAction action = new TeleportAction();
		assertThat(action.getMaximumParameters(), is(4));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final TeleportAction action = new TeleportAction();
		assertThat(action.getMinimumParameters(), is(4));
	}
}
