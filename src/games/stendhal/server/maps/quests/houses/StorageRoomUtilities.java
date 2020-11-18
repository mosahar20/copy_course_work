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

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

//import games.stendhal.common.filter.FilterCriteria;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
//import games.stendhal.server.entity.Entity;
//import games.stendhal.server.entity.mapstuff.chest.StoredChest;
import games.stendhal.server.entity.mapstuff.portal.StorageRoomPortal;
import games.stendhal.server.entity.mapstuff.portal.Portal;
import games.stendhal.server.entity.player.Player;

public class StorageRoomUtilities {
	private static List<StorageRoomPortal> allStorageRoomPortals = null;
	private static final String STORAGE_QUEST_SLOT = "storage_room";
	private static final Logger logger = Logger.getLogger(StorageRoomUtilities.class);
	private static final String[] zoneNames = {
		"int_ados_storage_depot",
		"int_deniran_storage_depot"
	};

	private StorageRoomUtilities() {
		// hide constructor, this is a static class
	}

	/**
	 * clears the house cache
	 */
	public static void clearCache() {
		 allStorageRoomPortals = null;
	}

	/**
	 * Get the house owned by a player.
	 *
	 * @param player the player to be examined
	 * @return portal to the house owned by the player, or <code>null</code>
	 * if he does not own one.
	 */
	protected static StorageRoomPortal getPlayersRoom(final Player player) {
		if (player.hasQuest(STORAGE_QUEST_SLOT)) {
			final String claimedHouse = player.getQuest(STORAGE_QUEST_SLOT);

			try {
				final int id = Integer.parseInt(claimedHouse);
				final StorageRoomPortal portal = getStorageRoomPortal(id);

				if (portal != null) {
					if (player.getName().equals(portal.getOwner())) {
						return portal;
					}
				} else {
					logger.error("Player " + player.getName() + " claims to own a nonexistent storage room " + id);
				}
			} catch (final NumberFormatException e) {
				logger.error("Invalid Storage Room Number", e);
			}
		}

		return null;
	}

	/**
	 * Check if a player owns a house.
	 *
	 * @param player the player to be checked
	 * @return <code>true</code> if the player owns a house, false otherwise
	 */
	protected static boolean playerOwnsRoom(final Player player) {
		return (getPlayersRoom(player) != null);
	}

	/**
	 * Find a portal corresponding to a house number.
	 *
	 * @param houseNumber the house number to find
	 * @return the portal to the house, or <code>null</code> if there is no
	 * house by number <code>id</code>
	 */
	protected static StorageRoomPortal getStorageRoomPortal(final int roomNumber) {
		final List<StorageRoomPortal> portals = getStorageRoomPortals();
		for (final StorageRoomPortal StorageRoomPortal : portals) {
			final int number = StorageRoomPortal.getPortalNumber();
			
			if (number == roomNumber) {
				return StorageRoomPortal;
			}
		}

		// if we got this far, we didn't find a match
		// (triggered by AdosHouseSellerTest.testAdosHouseSellerNoZones)
		logger.error("getStorageRoomPortal was given a number (" + Integer.toString(roomNumber) + ") it couldn't match a room portal for");
		return null;
	}

	/**
	 * Get a list of all house portals available to players.
	 *
	 * @return list of all house portals
	 */
	protected static List<StorageRoomPortal> getStorageRoomPortals() {
		if (allStorageRoomPortals == null) {
			// this is only done once per server run
			List<StorageRoomPortal> tempallStorageRoomPortals = new LinkedList<StorageRoomPortal>();

			for (final String zoneName : zoneNames) {
				final StendhalRPZone zone = SingletonRepository.getRPWorld().getZone(zoneName);
				if (zone == null) {
					logger.warn("Could not find zone " + zoneName);
				} else {
					for (final Portal portal : zone.getPortals()) {
						if (portal instanceof StorageRoomPortal) {
							tempallStorageRoomPortals.add((StorageRoomPortal) portal);
							
						}
					}
				}
			}
			allStorageRoomPortals = tempallStorageRoomPortals;
		}
		final int size = allStorageRoomPortals.size();
		logger.debug("Number of storage room portals in world is " + Integer.toString(size));

		return allStorageRoomPortals;
	}

	/**
	 * Find a chest corresponding to a house portal.
	 *
	 * @param portal the house portal of the house containing the chest we want to find
	 * @return the chest in the house, or <code>null</code> if there is no
	 * chest in the zone which the house portal leads to (Note, then, that chests should be on the 'ground floor')
	 

	protected static StoredChest findChest(final StorageRoomPortal portal) {
		final String zoneName = portal.getDestinationZone();
		final StendhalRPZone zone = SingletonRepository.getRPWorld().getZone(zoneName);

		final List<Entity> chests = zone.getFilteredEntities(new FilterCriteria<Entity>() {
			@Override
			public boolean passes(final Entity object) {
				return (object instanceof StoredChest);
			}
		});

		if (chests.size() != 1) {
			logger.error(chests.size() + " chests in " + portal.getDoorId());
			return null;
		}

		return (StoredChest) chests.get(0);
	}*/

	// this will be ideal for a seller to list all unbought houses
	// using Grammar.enumerateCollection
	private static List<String> getUnboughtRooms() {
	    final List<String> unbought = new LinkedList<String>();
		final List<StorageRoomPortal> portals =  getStorageRoomPortals();
		for (final StorageRoomPortal StorageRoomPortal : portals) {
			final String owner = StorageRoomPortal.getOwner();
			if (owner.length() == 0) {
				unbought.add(StorageRoomPortal.getDoorId());
			}
		}
		return unbought;
	}

	// this will be ideal for a seller to list all unbought houses in a specific location
	// using Grammar.enumerateCollection
	protected static List<String> getUnboughtRoomsInLocation(final String location) {
		final String regex = location + ".*";
		final List<String> unbought = new LinkedList<String>();
		for (final String doorId : getUnboughtRooms()) {
			if (doorId.matches(regex)) {
				unbought.add(doorId);
			}
		}
		return unbought;
	}

}
