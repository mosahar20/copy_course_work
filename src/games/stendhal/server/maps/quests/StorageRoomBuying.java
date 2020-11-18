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
package games.stendhal.server.maps.quests;

import java.util.LinkedList;

import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.quests.houses.StorageRoomBuyingMain;

public class StorageRoomBuying extends AbstractQuest {
	private static final String QUEST_SLOT = "storage_room";
	private StorageRoomBuyingMain quest;

	@Override
	public String getSlotName() {
		return QUEST_SLOT;
	}

	@Override
	public void addToWorld() {

		quest = new StorageRoomBuyingMain();
		quest.addToWorld();

		fillQuestInfo(
				"Storage Room Buying",
				"Storage Rooms can be bought across some cities in Faiumoni.",
				false);
	}

	@Override
	public LinkedList<String> getHistory(final Player player) {
		return quest.getHistory(player);
	}

	@Override
	public String getName() {
		return "StorageRoomBuying";
	}

	@Override
	public int getMinLevel() {
		return 0;
	}

	@Override
	public boolean isCompleted(final Player player) {
		return quest.isCompleted(player);
	}

	@Override
	public String getNPCName() {
		return "StorageDepot Guy";
	}
}
