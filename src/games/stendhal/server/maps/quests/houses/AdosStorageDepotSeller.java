/**
 *
 */
package games.stendhal.server.maps.quests.houses;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import games.stendhal.common.parser.ExpressionType;
import games.stendhal.common.parser.JokerExprMatcher;
import games.stendhal.server.core.pathfinder.FixedPath;
import games.stendhal.server.core.pathfinder.Node;
import games.stendhal.server.entity.npc.ConversationStates;
//import games.stendhal.server.entity.npc.condition.AgeGreaterThanCondition;
import games.stendhal.server.entity.npc.condition.AndCondition;
//import games.stendhal.server.entity.npc.condition.NotCondition;
//import games.stendhal.server.entity.npc.condition.QuestCompletedCondition;
import games.stendhal.server.entity.npc.condition.QuestNotStartedCondition;
import games.stendhal.server.entity.npc.condition.TextHasNumberCondition;

final class AdosStorageDepotSeller extends StorageDepotNPCBase {
	/** Cost to buy house in ados. */
	private static final int COST_ADOS = 1000;

	AdosStorageDepotSeller(final String name, final String location, final StorageRoomTax storageRoomTax) {
		super(name, location, storageRoomTax);
		init();
	}

	private void init() {
		// Other than the condition that you must not already own a storageRoom, there are a number of conditions a player must satisfy.
		// For definiteness we will check these conditions in a set order.
		// So then the NPC doesn't have to choose which reason to reject the player for (appears as a WARN from engine if he has to choose)

		// player is not old enough
		

		// player is eligible to buy a house
		add(ConversationStates.ATTENDING,
				Arrays.asList("cost", "storage","room", "buy", "purchase"),
				 new AndCondition(new QuestNotStartedCondition(StorageDepotNPCBase.QUEST_SLOT)),
				 ConversationStates.QUEST_OFFERED,
				 "The cost of a new storage room in Ados is "
				 + getCost()
				 + " money. Also, you must pay a storage room tax of " + StorageRoomTax.BASE_TAX
				 + " money, every month. If you have a storage room in mind, please tell me the number now. I will check availability. "
				 + "The Ados houses are numbered from "
				 + "1 to 6",
				 null);

		// handle storage room numbers getLowestHouseNumber() - getHighestHouseNumber()
		addMatching(ConversationStates.QUEST_OFFERED,
				 // match for all numbers as trigger expression
				ExpressionType.NUMERAL, new JokerExprMatcher(),
				new TextHasNumberCondition(getLowestRoomNumber(), getHighestRoomNumber()),
				ConversationStates.ATTENDING,
				null,
				new BuyStorageRoomChatAction(getCost(), QUEST_SLOT));

		addJob("Welcome to the Storage Depot. I sell storage rooms for the city of Ados. Please ask about the #cost if you are interested.");
		
		setDescription("Storage Depot, to rent storage rooms to store your items.");
		setEntityClass("estateagent2npc");
		setPosition(22, 46);
		initHP(100);

	}

	@Override
	protected int getCost() {
		return AdosStorageDepotSeller.COST_ADOS;
	}

	@Override
	protected void createPath() {
		final List<Node> nodes = new LinkedList<Node>();
		nodes.add(new Node(16, 46));
		nodes.add(new Node(16, 45));
		nodes.add(new Node(29, 45));
		nodes.add(new Node(29, 46));
		nodes.add(new Node(23, 46));
		setPath(new FixedPath(nodes, true));
	}

	@Override
	protected int getHighestRoomNumber() {
		return 6;
	}

	@Override
	protected int getLowestRoomNumber() {
		return 1;
	}

}
