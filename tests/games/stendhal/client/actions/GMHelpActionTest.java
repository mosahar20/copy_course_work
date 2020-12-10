package games.stendhal.client.actions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.client.MockStendhalClient;
import games.stendhal.client.StendhalClient;
import games.stendhal.client.util.UserInterfaceTestHelper;
//import marauroa.common.game.RPAction;

public class GMHelpActionTest {

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
//			@Override
//			public void send(final RPAction action) {
//				assertEquals("", action.get("type"));
//				assertEquals("", action.get(""));
//			}
		};
		final GMHelpAction action = new GMHelpAction();
		assertTrue(action.execute(new String[] {null} , "unimportant text" ));
		assertTrue(action.execute(new String[] {"support"} , "" ));
		assertTrue(action.execute(new String[] {"alter"} , "" ));
		assertTrue(action.execute(new String[] {"script"} , "" ));
		
		
	}
	
	
	
	
	/**
	 * Test for getMaximumParameters
	 */
	@Test
	public void testGetMaximumParameters() {
		final GMHelpAction action = new GMHelpAction();
		assertThat(action.getMaximumParameters(), is(1));
	}
	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final GMHelpAction action = new GMHelpAction();
		assertThat(action.getMinimumParameters(), is(0));
	}
	
}
