package games.stendhal.client.actions;

//import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

//import games.stendhal.client.MockStendhalClient;
import games.stendhal.client.StendhalClient;
import games.stendhal.client.util.UserInterfaceTestHelper;
//import marauroa.common.game.RPAction;

public class VolumeActionTest {


	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		StendhalClient.resetClient();
	}
	
	
	@Test
	public void testVolumeChange() {
		UserInterfaceTestHelper.initUserInterface();
		
		final VolumeAction action = new VolumeAction();
		assertTrue(action.execute(new String[] {null} , "unimportant text" ));
		assertTrue(action.execute(new String[] {"master","50"} , "unimportant text" ));
		assertTrue(action.execute(new String[] {"master",null} , "unimportant text" ));
	}
	
	
	
}
