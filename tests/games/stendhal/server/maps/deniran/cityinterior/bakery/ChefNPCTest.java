package games.stendhal.server.maps.deniran.cityinterior.bakery;

import static org.junit.Assert.*;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.After;
import org.junit.BeforeClass;
//import org.junit.After;
//import org.junit.BeforeClass;
import org.junit.Test;

//import games.stendhal.server.entity.item.StackableItem;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
//import games.stendhal.server.maps.deniran.cityinterior.bakery.ChefNPC;
import utilities.QuestHelper;
//import marauroa.common.game.RPObject.ID;
import utilities.ZonePlayerAndNPCTestImpl;


public class ChefNPCTest extends ZonePlayerAndNPCTestImpl {

	private static final String ZONE_NAME = "testzone";

	private static final String QUEST = "patrick_make_sandwiches";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
		setupZone(ZONE_NAME);
	}

	public ChefNPCTest() {
		setNpcNames("Patrick");
		setZoneForPlayer(ZONE_NAME);
		addZoneConfigurator(new ChefNPC(), ZONE_NAME);
	}

	@Override
	@After
	public void tearDown() throws Exception {
		super.tearDown();

		player.removeQuest(QUEST);
	}

	
	
	@Test
	public void testGreetings() {
		final SpeakerNPC npc = getNPC("Patrick");
		final Engine en = npc.getEngine();

		en.step(player, "hi");
		assertTrue(npc.isTalking());
		assertEquals(
				"Hello and welcome to Deniran Bakery.",
				getReply(npc));
	}
	
	@Test
	public void testJob() {
		final SpeakerNPC npc = getNPC("Patrick");
		final Engine en = npc.getEngine();

		en.step(player, "hi");
		assertTrue(npc.isTalking());
		assertEquals(
				"Hello and welcome to Deniran Bakery.",
				getReply(npc));
		
		en.step(player, "job");
		assertTrue(npc.isTalking());
		assertEquals(
				"I run Deniran Bakery. ",
				getReply(npc));
	}

}
