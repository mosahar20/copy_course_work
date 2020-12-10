/*
 * @(#) src/games/stendhal/server/config/ZoneGroupsXMLLoader.java
 *
 * $Id$
 */

package games.stendhal.server.core.config;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

//import games.stendhal.client.actions.SlashAction;
import marauroa.common.game.RPAction;

/**
 * Load and configure actionss via an XML configuration file.
 */
public class ActionGroupsXMLLoader extends DefaultHandler {

	private static final Logger LOGGER = Logger.getLogger(ActionGroupsXMLLoader.class);

	/** The main zone configuration file. */
	protected URI uri;

	/**
	 * Create an xml based loader of actions groups.
	 *
	 * @param uri
	 *            The location of the configuration file.
	 */
	public ActionGroupsXMLLoader(final URI uri) {
		this.uri = uri;
	}


	/**
	 * Create an xml based loader of actions groups.
	 *
	 * @param uri
	 *            The location of the configuration file.
	 */
	public ActionGroupsXMLLoader(final String uri) {
		try {
			this.uri = new URI(uri);
		} catch (URISyntaxException e) {
			LOGGER.error(e, e);
		}
	}

	/**
	 * Loads actionss
	 *
	 * @return list of all actionss.
	 */
	public List<RPAction> load() {
		final GroupsXMLLoader groupsLoader = new GroupsXMLLoader(uri);
		final List<RPAction> list = new LinkedList<RPAction>();
		try {
			List<URI> groups = groupsLoader.load();

			// Load each group
			for (final URI tempUri : groups) {
				final ActionXMLLoader loader = new ActionXMLLoader();

				try {
					list.addAll(loader.load(tempUri));
				} catch (final SAXException ex) {
					LOGGER.error("Error loading actions group: " + tempUri, ex);
				}
			}
		} catch (SAXException e) {
			LOGGER.error(e, e);
		} catch (IOException e) {
			LOGGER.error(e, e);
		}
		return list;
	}
}
