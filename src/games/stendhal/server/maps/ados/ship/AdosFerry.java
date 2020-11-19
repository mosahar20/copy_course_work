package games.stendhal.server.maps.ados.ship;

import java.util.LinkedList;
import java.util.List;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.events.TurnListener;
import games.stendhal.server.util.TimeUtil;

/**
 * 
 * this class just adapts the AthorFerry.java 
 * original @author daniel
 * 
 * 
 *
 */
public final class AdosFerry implements TurnListener {

	/** How much it costs to board the ferry. */
	public static final int PRICE = 200;

	/** The Singleton instance. */
	private static AdosFerry instance;

	private Status current;





	/**
	 * A list of non-player characters that get notice when the ferry arrives or
	 * departs, so that they can react accordingly, e.g. inform nearby players.
	 */
	private final List<IFerryListener> listeners;



	private AdosFerry() {
		listeners = new LinkedList<IFerryListener>();
		current = Status.ANCHORED_AT_ADOS;
	}

	/**
	 * @return The Singleton instance.
	 */
	public static AdosFerry get() {
		if (instance == null) {
			AdosFerry instance = new AdosFerry();

			// initiate the turn notification cycle
			SingletonRepository.getTurnNotifier().notifyInSeconds(1, instance);
			AdosFerry.instance = instance;
		}
		return instance;
	}

	public Status getState() {
		return current;
	}

	/**
	 * Gets a textual description of the ferry's status.
	 *
	 * @return A String representation of time remaining till next state.
	 */

	private String getRemainingSeconds() {
		final int secondsUntilNextState = SingletonRepository.getTurnNotifier()
				.getRemainingSeconds(this);
		return TimeUtil.approxTimeUntil(secondsUntilNextState);
	}

	/**
	 * Is called when the ferry has either arrived at or departed from a harbor.
	 * @param currentTurn the turn when this listener is called
	 */
	@Override
	public void onTurnReached(final int currentTurn) {
		// cycle to the next state

		current = current.next();
		for (final IFerryListener npc : listeners) {
			npc.onNewFerryState(current);
		}
		SingletonRepository.getTurnNotifier().notifyInSeconds(current.duration(), this);
	}

	public void addListener(final IFerryListener npc) {
		listeners.add(npc);
	}

	/**
	 * Auto registers the listener to AdosFerry.
	 * deregistration must be implemented if it is used for short living objects
	 * original @author astridemma
	 *
	 */
	public abstract static class FerryListener implements IFerryListener {
		public FerryListener() {
			SingletonRepository.getAdosFerry().addListener(this);
		}


	}

	public interface IFerryListener {
		void onNewFerryState(Status current);
	}

	public enum Status {
		ANCHORED_AT_ADOS {
			@Override
			Status next() {
				return DRIVING_TO_DENIRAN;
			}

			@Override
			int duration() {
				return 2 * 60;
			}

			@Override
			public String toString() {
				return "The ferry is currently anchored at Ados. It will take off in "
						+ SingletonRepository.getAdosFerry().getRemainingSeconds() + ".";
			}
		},
		DRIVING_TO_DENIRAN {
			@Override
			Status next() {
				return ANCHORED_AT_DENIRAN;
			}

			@Override
			int duration() {
				return 5 * 60;
			}

			@Override
			public String toString() {
				return "The ferry is currently sailing to Deniran. It will arrive in "
						+ SingletonRepository.getAdosFerry().getRemainingSeconds() + ".";
			}

		},
		ANCHORED_AT_DENIRAN {
			@Override
			Status next() {
				return DRIVING_TO_ADOS;
			}

			@Override
			int duration() {
				return 2 * 60;
			}

			@Override
			public String toString() {
				return "The ferry is currently anchored at Deniran. It will take off in "
						+ SingletonRepository.getAdosFerry().getRemainingSeconds() + ".";
			}

		},
		DRIVING_TO_ADOS{
			@Override
			Status next() {
				return ANCHORED_AT_ADOS;
			}

			@Override
			int duration() {
				return 5 * 60;
			}

			@Override
			public String toString() {
				return "The ferry is currently sailing to Ados. It will arrive in "
						+ SingletonRepository.getAdosFerry().getRemainingSeconds() + ".";
			}

		};

		/**
		 * gives the following status.
		 * @return the next Status
		 */
		abstract Status next();

		/**
		 * how long will this state last.
		 * @return time in seconds;
		 */
		abstract int duration();
	}

}
