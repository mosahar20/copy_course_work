package games.stendhal.client.actions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.client.MockStendhalClient;
import games.stendhal.client.StendhalClient;
import marauroa.common.game.RPAction;

public class TellAllActionTest {

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
				assertEquals("tellall", action.get("type"));
				assertEquals("some notes", action.get("text"));
			}
		};
		final TellAllAction action = new TellAllAction();
		assertTrue(action.execute(new String[] {null}, "some notes" ));
		
	}
	
	
	
	
	/**
	 * Test for getMaximumParameters
	 */
	@Test
	public void testGetMaximumParameters() {
		final TellAllAction action = new TellAllAction();
		assertThat(action.getMaximumParameters(), is(0));
	}
	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final TellAllAction action = new TellAllAction();
		assertThat(action.getMinimumParameters(), is(0));
	}
	
}
