package games.stendhal.client.actions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.client.MockStendhalClient;
import games.stendhal.client.StendhalClient;
import marauroa.common.game.RPAction;

public class AdminNoteActionTest {

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
				assertEquals("adminnote", action.get("type"));
				assertEquals("usernamex", action.get("target"));
				assertEquals("some note stuff", action.get("note"));
			}
		};
		final AdminNoteAction action = new AdminNoteAction();
		assertTrue(action.execute(new String[] {"usernamex"}, "some note stuff" ));
		
	}
	
	
	
	/**
	 * Test for getMaximumParameters
	 */
	@Test
	public void testGetMaximumParameters() {
		final AdminNoteAction action = new AdminNoteAction();
		assertThat(action.getMaximumParameters(), is(1));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final AdminNoteAction action = new AdminNoteAction();
		assertThat(action.getMinimumParameters(), is(1));
	}
	
}
