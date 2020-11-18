/**
 *
 */
package games.stendhal.server.maps.quests.houses;

import org.apache.log4j.Logger;

import games.stendhal.common.parser.Sentence;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.entity.item.StackableItem;
import games.stendhal.server.entity.mapstuff.portal.StorageRoomPortal;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.player.Player;

final class ResellStorageRoomAction implements ChatAction {

	private static final Logger logger = Logger.getLogger(ResellStorageRoomAction.class);

	private final int cost;

	private final String questSlot;

	private final int depreciationPercentage;

	private final StorageRoomTax storageRoomTax;

	ResellStorageRoomAction(final int cost, final String questSlot, final int deprecationPercentage, final StorageRoomTax storageRoomTax) {
		this.questSlot = questSlot;
		this.cost = cost;
		depreciationPercentage = deprecationPercentage;
		this.storageRoomTax = storageRoomTax;
	}

	@Override
	public void fire(final Player player, final Sentence sentence, final EventRaiser raiser) {

		// we need to find out where this house is so we know how much to refund them
		final String claimedStorageRoom = player.getQuest(questSlot);

		try {

			final int id = Integer.parseInt(claimedStorageRoom);
			final StorageRoomPortal portal = StorageRoomUtilities.getStorageRoomPortal(id);

			final int refund = (cost * depreciationPercentage) / 100 - storageRoomTax.getTaxDebt(portal);

			final StackableItem money = (StackableItem) SingletonRepository.getEntityManager().getItem("money");
			money.setQuantity(refund);
			player.equipOrPutOnGround(money);

			portal.changeLock();
			portal.setOwner("");
			// the player has sold the house. clear the slot
			player.removeQuest(questSlot);
			raiser.say("Thanks, here is your " + Integer.toString(refund)
					   + " money owed, from the storage room value, minus any owed taxes. Now that you don't own a room "
					   + "you would be free to buy another if you want to.");
		} catch (final NumberFormatException e) {
			logger.error("Invalid number in storage room slot", e);
			raiser.say("Sorry, something bad happened. I'm terribly embarassed.");
			return;
		}
	}
}
