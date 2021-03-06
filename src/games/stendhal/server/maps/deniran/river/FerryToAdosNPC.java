package games.stendhal.server.maps.deniran.river;

import java.util.Map;

import games.stendhal.common.Direction;
import games.stendhal.common.parser.Sentence;
import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.ados.ship.AdosFerry;
import games.stendhal.server.maps.ados.ship.AdosFerry.Status;

public class FerryToAdosNPC implements ZoneConfigurator {
	
	@Override
	public void configureZone(StendhalRPZone zone,
			Map<String, String> attributes) {
		buildNPC(zone);
	}

	protected Status ferrystate;
	private static StendhalRPZone shipZone;

	public static StendhalRPZone getShipZone() {
		if (shipZone == null) {
			shipZone = SingletonRepository.getRPWorld().getZone("0_ados_ship_e2");
		}
		return shipZone;
	}
	
	private void buildNPC(StendhalRPZone zone) {
		final SpeakerNPC npc = new SpeakerNPC("Alice") {

			@Override
			protected void createPath() {
				setPath(null);
			}

			@Override
			public void createDialog() {
				addGreeting("Welcome to the ferry service from Deniran to Ados! How can I help you?");
				addGoodbye("Hope you will use this service next time.");
				addHelp("You will need " + AdosFerry.PRICE + " golds to board the ferry to Ados");
				add(ConversationStates.ATTENDING, "status",
						null,
						ConversationStates.ATTENDING,
						null,
						new ChatAction() {
							@Override
							public void fire(final Player player, final Sentence sentence, final EventRaiser npc) {
								npc.say(ferrystate.toString());
							}
						});
				add(ConversationStates.ATTENDING,
						"board",
						null,
						ConversationStates.ATTENDING,
						null,
						new ChatAction() {
							@Override
							public void fire(final Player player, final Sentence sentence, final EventRaiser npc) {
								if (player.drop("money", AdosFerry.PRICE)) {
									npc.say("Welcome to the boat!!!!");
									player.teleport(getShipZone(), 27, 33, Direction.LEFT, null);

								} else {
									npc.say("You do not have enough money to board the boat to Ados!!!");
								}
							}
						});
			}
		};
		
		new AdosFerry.FerryListener() {
			@Override
			public void onNewFerryState(final Status status) {
				ferrystate = status;
				switch (status) {
				case ANCHORED_AT_DENIRAN:
					npc.say("Attention: The ferry has arrived at this coast! You can now #board the ship.");
					break;
				case DRIVING_TO_ADOS:
					npc.say("Attention: The ferry has taken off. You can no longer board it.");
					break;
				default:
					break;
				}
			}
		};
		
		npc.setPosition(41, 25);
		npc.setDescription("You see Alice. She takes customers on board of the Deniran to Ados ferry.");
		npc.setEntityClass("woman_002_npc");
		npc.setDirection(Direction.RIGHT);
		zone.add(npc);
	}
}
