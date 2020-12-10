package games.stendhal.client.actions;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.client.MockStendhalClient;
import games.stendhal.client.StendhalClient;
import games.stendhal.client.util.UserInterfaceTestHelper;

public class HelpActionTest {

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
		
		};
		final HelpAction action = new HelpAction();
		assertTrue(action.execute(new String[] {null}, null ));
		
	}
	
	
	
	
}
