package games.stendhal.client.actions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.client.MockStendhalClient;
import games.stendhal.client.StendhalClient;
import marauroa.common.game.RPAction;

public class SupportAnswerActionTest {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		StendhalClient.resetClient();
	}

	@Test
	public void testexecute() {
		new MockStendhalClient() {
			@Override
			public void send(final RPAction action) {
				assertEquals("supportanswer", action.get("type"));
				assertEquals("jerry", action.get("target"));
				assertEquals("help", action.get("text"));
			}
		};
		final SupportAnswerAction action = new SupportAnswerAction();
		assertTrue(action.execute(new String[] {"jerry"}, "help"));
	}
	
	@Test
	public void testGetMaximumParameters() {
		final SupportAnswerAction action = new SupportAnswerAction();
		assertThat(action.getMaximumParameters(), is(1));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final SupportAnswerAction action = new SupportAnswerAction();
		assertThat(action.getMinimumParameters(), is(1));
	}
	
}
