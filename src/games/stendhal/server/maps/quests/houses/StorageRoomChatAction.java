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

class StorageRoomChatAction {

	/** Cost to buy spare keys. */
	static final int COST_OF_SPARE_KEY = 100;
	protected String questslot;

	protected StorageRoomChatAction(final String questslot) {
		this.questslot = questslot;
	}
}
