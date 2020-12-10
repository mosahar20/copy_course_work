package games.stendhal.client.actions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.client.StendhalClient;
import games.stendhal.client.util.UserInterfaceTestHelper;
import marauroa.common.game.RPAction;
import games.stendhal.client.MockStendhalClient;

public class ClientInfoActionTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		StendhalClient.resetClient();
	}
	
	
	
	@Test
	public void testAction() {
		UserInterfaceTestHelper.initUserInterface();
		
		new MockStendhalClient() {
			@Override
			public void send(final RPAction tell) {
				assertEquals("support", tell.get("type"));
			}
		};
		
		final ClientInfoAction action = new ClientInfoAction();
		assertTrue(action.execute(new String[] {null}, null ));
		
	}
	
	
	
	
	/**
	 * Test for getMaximumParameters
	 */
	@Test
	public void testGetMaximumParameters() {
		final ClientInfoAction action = new ClientInfoAction();
		assertThat(action.getMaximumParameters(), is(0));
	}
	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final ClientInfoAction action = new ClientInfoAction();
		assertThat(action.getMinimumParameters(), is(0));
	}
	
}
