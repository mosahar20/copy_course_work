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
package games.stendhal.server.entity.item;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.Entity;
import games.stendhal.server.entity.mapstuff.spawner.FlowerGrower;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import utilities.PlayerTestHelper;
import utilities.RPClass.GrowingPassiveEntityRespawnPointTestHelper;

public class flowerpotTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MockStendlRPWorld.get();
		GrowingPassiveEntityRespawnPointTestHelper.generateRPClasses();
	}



	/**
	 * Tests for execute.
	 */
	@Test
	public void testExecute() {
		final FlowerPot fp = (FlowerPot) SingletonRepository.getEntityManager().getItem("flowerpot");
		final Player player = PlayerTestHelper.createPlayer("bob");
		assertNotNull(player);
		final StendhalRPZone zone = new StendhalRPZone("zone");
		SingletonRepository.getRPWorld().addRPZone(zone);
		zone.add(player);

		assertNotNull(fp);
		zone.add(fp);
		fp.setPosition(1, 0);

		assertTrue(fp.onUsed(player));

		assertNotNull(player.getZone().getEntityAt(1, 0));
		assertTrue(player.getZone().getEntityAt(1, 0) instanceof FlowerGrower);

	}


	
	/**
	 * Tests for executeNonameSeed.
	 */
	@Test
	public void testExecuteNonameFlowerPot() {
		final FlowerPot fp = (FlowerPot) SingletonRepository.getEntityManager().getItem("flowerpot");
		final Player player = PlayerTestHelper.createPlayer("bob");
		assertNotNull(player);
		final StendhalRPZone zone = new StendhalRPZone("zone");
		SingletonRepository.getRPWorld().addRPZone(zone);
		zone.add(player);

		assertNotNull(fp);
		zone.add(fp);
		fp.setPosition(1, 0);

		assertTrue(fp.onUsed(player));

		final Entity entity = player.getZone().getEntityAt(1, 0);
		assertNotNull(entity);
		if (entity instanceof FlowerGrower) {
			final FlowerGrower flg = (FlowerGrower) entity;
			flg.setToFullGrowth();
			flg.onUsed(player);
			assertNull(player.getZone().getEntityAt(1, 0));
			assertTrue(player.isEquipped("lilia"));
		} else {
			fail("flowerpot produced non flowergrower");
		}


	}

	
}
