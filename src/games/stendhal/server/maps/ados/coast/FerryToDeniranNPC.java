package games.stendhal.server.maps.ados.coast;

import java.util.Map;

import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.SpeakerNPC;

public class FerryToDeniranNPC implements ZoneConfigurator {
	
	@Override
	public void configureZone(StendhalRPZone zone,
			Map<String, String> attributes) {
		buildNPC(zone);
	}

	private void buildNPC(StendhalRPZone zone) {
		final SpeakerNPC npc = new SpeakerNPC("Sam") {

			@Override
			protected void createPath() {
				setPath(null);
			}

			@Override
			public void createDialog() {
				addGreeting("Welcome to the ferry service from Ados to Deniran! How can I help you?");
				addGoodbye("Hope you will use this service next time.");
				addHelp("You will need 200 golds to board the ferry to Deniran");
			}
		};
		zone.add(npc);
	}
}
