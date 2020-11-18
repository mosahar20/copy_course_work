package games.stendhal.server.maps.deniran.river;

import static org.junit.Assert.*;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.entity.player.Player;
import utilities.QuestHelper;
import utilities.ZonePlayerAndNPCTestImpl;

public class FerryToAdosNPCTest extends ZonePlayerAndNPCTestImpl{

	private static final String ZONE_NAME = "0_deniran_river_s";
	private static final String NPC_NAME = "Sam";
	
	private Player player;
	private SpeakerNPC npc;
	private Engine engine;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
		setupZone(ZONE_NAME);
	}

	public FerryToAdosNPCTest() {
		setNpcNames(NPC_NAME);
		setZoneForPlayer(ZONE_NAME);
		addZoneConfigurator(new FerryToAdosNPC(), ZONE_NAME);
	}
	
	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();

		player = createPlayer("player");
		npc = SingletonRepository.getNPCList().get(NPC_NAME);
		engine = npc.getEngine();
	}
	
	@Test
	public void testGreetingsandBye() {
		engine.step(player, "hi");
		assertTrue(npc.isTalking());
		assertEquals(
				"Welcome to the ferry service from Ados to Deniran! How can I help you?",
				getReply(npc));
		engine.step(player, "bye");
		assertTrue(npc.isTalking());
		assertEquals(
				"Hope you will use this service next time.",
				getReply(npc));
	}
	
	@Test
	public void helpConvo() {
		engine.step(player, "hi");
		assertTrue(npc.isTalking());
		assertEquals(
				"Welcome to the ferry service from Ados to Deniran! How can I help you?",
				getReply(npc));
		
		engine.step(player, "help");
		assertTrue(npc.isTalking());
		assertEquals(
				"You will need 200 golds to board the ferry to Ados",
				getReply(npc));
	}
	
	@Test
	public void notEnoughMoneyConvo() {
		engine.step(player, "hi");
		assertTrue(npc.isTalking());
		assertEquals(
				"Welcome to the ferry service from Ados to Deniran! How can I help you?",
				getReply(npc));
		engine.step(player, "board");
		assertTrue(npc.isTalking());
		assertEquals("You do not have enough money to board the boat to Ados!!!", getReply(npc));
	}
	
	@Test
	public void EnoughMoneyConvo() {
		assertTrue(equipWithMoney(player, 200));
		engine.step(player, "hi");
		assertTrue(npc.isTalking());
		assertEquals(
				"Welcome to the ferry service from Ados to Deniran! How can I help you?",
				getReply(npc));
		engine.step(player, "board");
		assertTrue(npc.isTalking());
		assertEquals("Welcome to the boat!!!!", getReply(npc));
	}
}
