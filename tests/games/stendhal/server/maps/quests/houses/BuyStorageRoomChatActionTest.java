/* $Id$ */
/***************************************************************************
 *                   (C) Copyright 2003-2010 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.maps.quests.houses;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.common.parser.ConversationParser;
import games.stendhal.common.parser.Sentence;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.mapstuff.portal.StorageRoomPortal;
import games.stendhal.server.entity.mapstuff.portal.Portal;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendhalRPRuleProcessor;
import games.stendhal.server.maps.MockStendlRPWorld;
import utilities.PlayerTestHelper;

public class BuyStorageRoomChatActionTest {
	private StorageRoomPortal storageRoomPortal;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PlayerTestHelper.generateNPCRPClasses();
		Portal.generateRPClass();
		StorageRoomPortal.generateRPClass();
		MockStendlRPWorld.get();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		StorageRoomUtilities.clearCache();
		MockStendhalRPRuleProcessor.get().clearPlayers();
	}

	@Before
	public void setUp() throws Exception {
	}
	@After
	public void clearStored() {
		if (storageRoomPortal != null) {
			StendhalRPZone zone = storageRoomPortal.getZone();
			if (zone != null) {
				zone.remove(storageRoomPortal);
				storageRoomPortal = null;
			}
		}
	}

	/**
	 * Tests for fire.
	 */
	@Test
	public void testFire() {
		BuyStorageRoomChatAction action = new BuyStorageRoomChatAction(1, StorageDepotNPCBase.QUEST_SLOT);
		String zoneName = "int_ados_storage_depot";
		StendhalRPZone ados = new StendhalRPZone(zoneName);
		MockStendlRPWorld.get().addRPZone(ados);
		storageRoomPortal = new StorageRoomPortal("schnick bla 1");
		storageRoomPortal.setIdentifier("keep rpzone happy");
		storageRoomPortal.setDestination(zoneName, "schnick bla 1");
		ados.add(storageRoomPortal);
		StorageRoomUtilities.clearCache();

		SpeakerNPC engine = new SpeakerNPC("bob");
		EventRaiser raiser = new EventRaiser(engine);
		Player player = PlayerTestHelper.createPlayer("george");
		Sentence sentence = ConversationParser.parse("1");
		action.fire(player , sentence , raiser);
		assertThat(getReply(engine), is("You do not have enough money to buy a storage room!"));
		storageRoomPortal.setOwner("jim");

		action.fire(player , sentence , raiser);
		assertThat(getReply(engine), containsString("Sorry, room 1 is sold"));

		PlayerTestHelper.equipWithMoney(player, 1);

		storageRoomPortal.setOwner("");

		action.fire(player , sentence , raiser);
		assertThat(getReply(engine), containsString("Congratulation"));
		assertFalse(player.isEquipped("money"));
	}

}
