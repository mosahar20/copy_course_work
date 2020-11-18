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

import games.stendhal.common.MathHelper;
import games.stendhal.common.grammar.Grammar;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.mapstuff.portal.StorageRoomPortal;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.player.Player;

/**
 * Controls house buying.
 *
 * @author kymara
 */
public class StorageRoomBuyingMain {
	private StorageRoomTax storageRoomTax = null;

	/** Ados Storage Depot. */
	private static final String INT_ADOS_STORAGE_DEPOT = "int_ados_storage_depot";
	private static final String INT_DENIRAN_STORAGE_DEPOT = "int_deniran_storage_depot";

	/**
	 * The NPC for Kalavan Houses.
	 *
	 * @param zone target zone
	 */
	public void createAdosNPC(StendhalRPZone zone) {
		final SpeakerNPC npc = new AdosStorageDepotSeller("StorageDepot Guy", "ados", storageRoomTax);
		zone.add(npc);
	}
	public void createDeniranNPC(StendhalRPZone zone) {
		final SpeakerNPC npc = new DeniranStorageDepotSeller("DeniranStorageDepot Guy", "deniran", storageRoomTax);
		zone.add(npc);
	}

	

	public LinkedList<String> getHistory(final Player player) {
		LinkedList<String> hist = new LinkedList<String>();
		if(!player.hasQuest("storage_room")) {
			hist.add("I've never bought a storage room.");
			return(hist);
		}
		hist.add("I bought " +  StorageRoomUtilities.getStorageRoomPortal(MathHelper.parseInt(player.getQuest("storage_room"))).getDoorId() + ".");
		StorageRoomPortal playerRoomPortal = StorageRoomUtilities.getPlayersRoom(player);
		if(playerRoomPortal!=null) {
			int unpaidPeriods = storageRoomTax.getUnpaidTaxPeriods(player);
			if (unpaidPeriods>0) {
				hist.add("I owe " + Grammar.quantityplnoun(unpaidPeriods, "month", "one") + " worth of tax.");
			} else {
				hist.add("I am up to date with my storage room tax payments.");
			}
		} else {
			hist.add("I no longer own that storage room.");
		}
		return(hist);
	}

	public void addToWorld() {
		// Start collecting taxes as well
		storageRoomTax = new StorageRoomTax();

		StendhalRPZone zone = SingletonRepository.getRPWorld().getZone(INT_ADOS_STORAGE_DEPOT);
		createAdosNPC(zone);
		zone = SingletonRepository.getRPWorld().getZone(INT_DENIRAN_STORAGE_DEPOT);
		createDeniranNPC(zone);

	}

	public boolean isCompleted(final Player player) {
		return StorageRoomUtilities.getPlayersRoom(player)!=null;
	}
}
