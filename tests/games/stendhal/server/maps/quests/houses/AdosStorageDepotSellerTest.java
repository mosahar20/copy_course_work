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

import static games.stendhal.server.entity.npc.ConversationStates.ATTENDING;
import static games.stendhal.server.entity.npc.ConversationStates.QUEST_OFFERED;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.item.StackableItem;
import games.stendhal.server.entity.mapstuff.portal.StorageRoomPortal;
import games.stendhal.server.entity.mapstuff.portal.Portal;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendhalRPRuleProcessor;
import games.stendhal.server.maps.MockStendlRPWorld;
import utilities.PlayerTestHelper;

public class AdosStorageDepotSellerTest {

	private StorageRoomPortal storageRoomPortal;
	private AdosStorageDepotSeller seller;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PlayerTestHelper.generateNPCRPClasses();
		Portal.generateRPClass();
		StorageRoomPortal.generateRPClass();
		MockStendlRPWorld.get();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		SingletonRepository.getNPCList().clear();
		MockStendhalRPRuleProcessor.get().clearPlayers();
		StorageRoomUtilities.clearCache();
	}

	@Before
	public void setUp() {
		SingletonRepository.getNPCList().add(new SpeakerNPC("Mr Taxman"));
		seller = new AdosStorageDepotSeller("bob", "nirvana", new StorageRoomTax());
	}

	/**
	 * Remove added stored entities.
	 * <p>
	 * stored entities can pollute the database
	 * if a server is ran on the same system as the tests.
	 */
	@After
	public void clearStored() {
		if (storageRoomPortal != null) {
			StendhalRPZone zone = storageRoomPortal.getZone();
			if (zone != null) {
				zone.remove(storageRoomPortal);
				storageRoomPortal = null;
			}
		}

		PlayerTestHelper.removeNPC("Mr Taxman");
	}

	/**
	 * Tests for getCost.
	 */
	@Test
	public void testGetCost() {
		assertEquals(1000, seller.getCost());
	}

	/**
	 * Tests for getLowestStorageRoomNumber.
	 */
	@Test
	public void testGetLowestStorageRoomNumber() {
		assertEquals(1, seller.getLowestRoomNumber());
	}

	/**
	 * Tests for getHighestStorageRoomNumber.
	 */
	@Test
	public void testGetHighestStorageRoomNumber() {
		assertEquals(6, seller.getHighestRoomNumber());
		assertThat(seller.getLowestRoomNumber(), is(lessThan(seller.getHighestRoomNumber())));
	}

	/**
	 * Tests for adosStorageRoomSellerNoZones.
	 */
	@Test
	public void testAdosStorageRoomSellerNoZones() {
		StorageRoomUtilities.clearCache();
		Engine en = seller.getEngine();
		en.setCurrentState(QUEST_OFFERED);

		Player george = PlayerTestHelper.createPlayer("george");

		en.step(george, "1");
		assertThat("no zones loaded", getReply(seller), is("Sorry I did not understand you, could you try saying the storage room number you want again please?"));
	}

	/**
	 * Tests for adosStorageRoomSeller.
	 */
	@Test
	public void testAdosStorageRoomSeller() {
		String zoneName = "int_ados_storage_depot";
		StendhalRPZone ados = new StendhalRPZone(zoneName);
		MockStendlRPWorld.get().addRPZone(ados);
		storageRoomPortal = new StorageRoomPortal("schnick bla 1");
		storageRoomPortal.setDestination(zoneName, "schnick bla 1");
		storageRoomPortal.setIdentifier("keep rpzone happy");
		ados.add(storageRoomPortal);
		StorageRoomUtilities.clearCache();

		Engine en = seller.getEngine();
		en.setCurrentState(QUEST_OFFERED);


		Player george = PlayerTestHelper.createPlayer("george");

		en.step(george, "1");
		assertThat("no zones loaded", getReply(seller), is("You do not have enough money to buy a storage room!"));
		assertThat(en.getCurrentState(), is(ATTENDING));

		en.setCurrentState(QUEST_OFFERED);

		StackableItem money = (StackableItem) SingletonRepository.getEntityManager().getItem("money");
		money.setQuantity(1000);
		george.equipToInventoryOnly(money);
		assertFalse(george.isEquipped("storage room key"));
		assertTrue(george.isEquipped("money", 1000));
		en.step(george, "1");
		assertThat(getReply(seller), containsString("Congratulations"));
		assertFalse(george.isEquipped("money", 1000));
		assertTrue(george.isEquipped("george's storage room key"));

	}

}
