package games.stendhal.client.actions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.client.MockStendhalClient;
import games.stendhal.client.StendhalClient;
import marauroa.common.game.RPAction;

public class AutoWalkActionTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		StendhalClient.resetClient();
	}
	
	
	@Test
	public void testActionWithExtraText() {
		new MockStendhalClient() {
			@Override
			public void send(final RPAction action) {
				assertEquals("walk", action.get("type"));
			}
		};
		final AutoWalkAction action = new AutoWalkAction();
		assertTrue(action.execute(new String[] {"usernamex"}, "some note stuff" ));
		
	}
	
	@Test
	public void testAction() {
		new MockStendhalClient() {
			@Override
			public void send(final RPAction action) {
				assertEquals("walk", action.get("type"));
			}
		};
		final AutoWalkAction action = new AutoWalkAction();
		assertTrue(action.execute(new String[] {null}, ""));
		
	}
	
	
	
	
	/**
	 * Test for getMaximumParameters
	 */
	@Test
	public void testGetMaximumParameters() {
		final AutoWalkAction action = new AutoWalkAction();
		assertThat(action.getMaximumParameters(), is(0));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final AutoWalkAction action = new AutoWalkAction();
		assertThat(action.getMinimumParameters(), is(0));
	}

}
