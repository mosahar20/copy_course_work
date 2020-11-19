
package games.stendhal.server.maps.quests.houses;


//import org.apache.log4j.Logger;

import games.stendhal.common.parser.Sentence;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.entity.item.StorageRoomKey;
import games.stendhal.server.entity.item.Item;
//import games.stendhal.server.entity.item.StackableItem;
//import games.stendhal.server.entity.mapstuff.chest.StoredChest;
import games.stendhal.server.entity.mapstuff.portal.StorageRoomPortal;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.player.Player;
//import marauroa.common.game.RPSlot;
//import marauroa.common.game.SlotIsFullException;

final class BuyStorageRoomChatAction extends StorageRoomChatAction implements ChatAction {


	private int cost;

	/**
	 * Creates a new BuyStorageRoomChatAction.
	 *
	 * @param cost how much does the house cost
	 * @param questSlot name of quest slot
	 */
	BuyStorageRoomChatAction(final int cost, final String questSlot) {
		super(questSlot);
		this.cost = cost;
	}

	@Override
	public void fire(final Player player, final Sentence sentence, final EventRaiser raiser) {

		final int number = sentence.getNumeral().getAmount();
		// now check if the house they said is free
		final String itemName = Integer.toString(number);

		final StorageRoomPortal storageRoomportal = StorageRoomUtilities.getStorageRoomPortal(number);

		if (storageRoomportal == null) {
			// something bad happened
			raiser.say("Sorry I did not understand you, could you try saying the storage room number you want again please?");
			raiser.setCurrentState(ConversationStates.QUEST_OFFERED);
			return;
		}

		final String owner = storageRoomportal.getOwner();
		if (owner.length() == 0) {

			// it's available, so take money
			if (player.isEquipped("money", cost)) {
				final Item key = SingletonRepository.getEntityManager().getItem(
																				"storage room key");

				final String doorId = storageRoomportal.getDoorId();

				final int locknumber = storageRoomportal.getLockNumber();
				((StorageRoomKey) key).setup(doorId, locknumber, player.getName());

				if (player.equipToInventoryOnly(key)) {
					raiser.say("Congratulations, here is your key to " + doorId
							   + "! Make sure you change the locks if you ever lose it. Do you want to buy a spare key, at a price of "
							   + StorageRoomChatAction.COST_OF_SPARE_KEY + " money?");

					player.drop("money", cost);
					// remember what house they own
					player.setQuest(questslot, itemName);

					// put nice things and a helpful note in the chest
					//BuyStorageRoomChatAction.fillChest(StorageRoomUtilities.findChest(houseportal), houseportal.getDoorId());

					// set the time so that the taxman can start harassing the player
					final long time = System.currentTimeMillis();
					storageRoomportal.setExpireTime(time);

					storageRoomportal.setOwner(player.getName());
					raiser.setCurrentState(ConversationStates.QUESTION_1);
				} else {
					raiser.say("Sorry, you can't carry more keys!");
				}

			} else {
				raiser.say("You do not have enough money to buy a storage room!");
			}

		} else {
			raiser.say("Sorry, room " + itemName
					   + " is sold, please ask for a list of #unsold storage room, or give me the number of another room.");
			raiser.setCurrentState(ConversationStates.QUEST_OFFERED);
		}
	}

	/*private static void fillChest(final StoredChest chest, String id) {
		Iterator<RPSlot> chessItemList = chest.slotsIterator();	//Iterate through current chest items
		while (chessItemList.hasNext()) 						//To delete any current items
			chessItemList.next().clear();
		
		Item item = SingletonRepository.getEntityManager().getItem("note");
		item.setDescription("WELCOME TO THE HOUSE OWNER\n"
				+ "1. If you do not pay your house taxes, the house and all the items in the chest will be confiscated.\n"
				+ "2. All people who can get in the house can use the chest.\n"
				+ "3. Remember to change your locks as soon as the security of your house is compromised.\n"
				+ "4. You can resell your house to the state if wished (please don't leave me)\n");
		try {
			chest.add(item);

			item = SingletonRepository.getEntityManager().getItem("wine");
			((StackableItem) item).setQuantity(2);
			chest.add(item);

			item = SingletonRepository.getEntityManager().getItem("chocolate bar");
			((StackableItem) item).setQuantity(2);
			chest.add(item);
			
			
				
		} catch (SlotIsFullException e) {
			Logger.getLogger(BuyStorageRoomChatAction.class).info("Could not add " + item.getName() + " to chest in " + id, e);
		}
	}*/
}
