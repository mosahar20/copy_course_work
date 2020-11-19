package games.stendhal.server.maps.ados.ship;

import java.util.Arrays;
import java.util.Map;

import games.stendhal.common.Direction;
import games.stendhal.common.parser.Sentence;
import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.ados.ship.AdosFerry.Status;


public class ShipToLandNPC implements ZoneConfigurator{
	
	@Override
	public void configureZone(StendhalRPZone zone,
			Map<String, String> attributes) {
		buildNPC(zone);
	}
	
	private static StendhalRPZone deniran;
	private static StendhalRPZone ados;
	
	private StendhalRPZone getDeniranZone() {
		if (deniran == null) {

			deniran = SingletonRepository.getRPWorld().getZone("0_deniran_river_s");
		}

		return deniran;
	}
	
	private StendhalRPZone getAdosZone() {
		if (ados == null) {

			ados = SingletonRepository.getRPWorld().getZone("0_ados_coast_s_w2");
		}

		return ados;
	}
	
	private Status ferryState;
	
	private void buildNPC(StendhalRPZone zone) {
		final SpeakerNPC npc = new SpeakerNPC("Jacky") {
			
			@Override
			protected void createPath() {
				setPath(null);
			}
			
			@Override
			public void createDialog() {
				addGoodbye("Goodbye!");
				addGreeting("Ahoy, Matey! How can I #help you?");
				addHelp("You can #disembark, but only when we're anchored a harbor. Just ask me for the #status if you have no idea where we are.");
				add(ConversationStates.ATTENDING, "status",
						null,
						ConversationStates.ATTENDING,
						null,
						new ChatAction() {
					@Override
					public void fire(final Player player, final Sentence sentence, final EventRaiser npc) {
						npc.say(ferryState.toString());
					}
				});
				
				add(ConversationStates.ATTENDING,
						Arrays.asList("disembark", "leave"),
						null,
						ConversationStates.ATTENDING,
						null,
						new ChatAction() {
					@Override
					public void fire(final Player player, final Sentence sentence, final EventRaiser npc) {
						switch (ferryState) {
						case ANCHORED_AT_ADOS:
							npc.say("We have reach our destination, do you want to leave?");
							npc.setCurrentState(ConversationStates.SERVICE_OFFERED);
							break;
						case ANCHORED_AT_DENIRAN:
							npc.say("We have reach our destination, do you want to leave?");
							npc.setCurrentState(ConversationStates.SERVICE_OFFERED);
							break;

						default:
							npc.say(ferryState.toString()
									+ " Ye can only get off the boat when it's anchored near a harbor.");
						}
					}
				});
				
				add(ConversationStates.SERVICE_OFFERED,
						ConversationPhrases.YES_MESSAGES,
						null,
						ConversationStates.ATTENDING, null,
						new ChatAction() {
					@Override
					public void fire(final Player player, final Sentence sentence, final EventRaiser npc) {
						switch (ferryState) {
						case ANCHORED_AT_ADOS:
							player.teleport(getAdosZone(), 100, 112, Direction.LEFT, null);
							npc.setCurrentState(ConversationStates.IDLE);
							break;
						case ANCHORED_AT_DENIRAN:
							player.teleport(getDeniranZone(), 43, 25, Direction.LEFT, null);
							npc.setCurrentState(ConversationStates.IDLE);
							break;

						default:
							npc.say("Too bad! The ship has already set sail.");

						}

					}
				});
				
				add(ConversationStates.SERVICE_OFFERED,
						ConversationPhrases.NO_MESSAGES,
						null,
						ConversationStates.ATTENDING,
						"Aye, matey but the ferry might sail away again!", null);

			}
		};
		
		new AdosFerry.FerryListener() {


			@Override
			public void onNewFerryState(final Status status) {
				ferryState = status;
				switch (status) {
				case ANCHORED_AT_ADOS:
					npc.say("Attention: The ferry has arrived at the ADOS! You can now #disembark.");
					break;
				case ANCHORED_AT_DENIRAN:
					npc.say("Attention: The ferry has arrived at the DENIRAN! You can now #disembark.");
					break;
				default:
					npc.say("Attention: The ferry has set sail.");
					break;
				}

			}
		};
		
		npc.setPosition(29, 34);
		npc.setEntityClass("pirate_sailor2npc");
		npc.setDescription ("Jacky helps passangers to disembark to the coast.");
		npc.setDirection(Direction.LEFT);
		zone.add(npc);
	}
}
