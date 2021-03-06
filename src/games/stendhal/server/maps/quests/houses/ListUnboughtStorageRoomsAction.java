/**
 *
 */
package games.stendhal.server.maps.quests.houses;

import java.util.List;

import games.stendhal.common.grammar.Grammar;
import games.stendhal.common.parser.Sentence;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.player.Player;

final class ListUnboughtStorageRoomsAction implements ChatAction {
	private final String location;

	/**
	 * Creates a new ListUnboughtHousesAction.
	 *
	 * @param location
	 *            where are the houses?
	 */
	ListUnboughtStorageRoomsAction(final String location) {
		this.location = location;
	}

	@Override
	public void fire(final Player player, final Sentence sentence, final EventRaiser raiser) {
		final List<String> unbought = StorageRoomUtilities.getUnboughtRoomsInLocation(location);
		if (unbought.size() > 0) {
			raiser.say("According to my records, " + Grammar.enumerateCollection(unbought) + " are all available for #purchase.");
		} else {
			raiser.say("Sorry, there are no storage rooms available for sale in " + Grammar.makeUpperCaseWord(location) + ".");
		}
	}
}
