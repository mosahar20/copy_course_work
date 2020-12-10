package games.stendhal.client.actions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.client.MockStendhalClient;
import games.stendhal.client.StendhalClient;
import marauroa.common.game.RPAction;

public class InvisibleActionTest {
	
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
				assertEquals("invisible", action.get("type"));
			}
		};
		final InvisibleAction action = new InvisibleAction();
		assertTrue(action.execute(new String[]{}, null));
	}
	
	@Test
	public void testGetMaximumParameters() {
		final InvisibleAction action = new InvisibleAction();
		assertThat(action.getMaximumParameters(), is(0));
	}
	
	@Test
	public void testGetMinimumParameters() {
		final InvisibleAction action = new InvisibleAction();
		assertThat(action.getMinimumParameters(), is(0));
	}

}
