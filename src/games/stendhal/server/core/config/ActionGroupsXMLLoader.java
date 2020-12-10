/*
 * @(#) src/games/stendhal/server/config/ZoneGroupsXMLLoader.java
 *
 * $Id$
 */

package games.stendhal.server.core.config;

import java.io.IOException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import games.stendhal.client.actions.SlashAction;



/**
 * Load and configure creatures via an XML configuration file.
 */
public class ActionGroupsXMLLoader extends DefaultHandler {

	private static final Logger LOGGER = Logger.getLogger(ActionGroupsXMLLoader.class);

	/** The main zone configuration file. */
	protected URI uri;

	/**
	 * Create an xml based loader of creature groups.
	 *
	 * @param uri
	 *            The location of the configuration file.
	 */
	public ActionGroupsXMLLoader(final URI uri) {
		this.uri = uri;
	}

	

	/**
	 * Create an xml based loader of creature groups.
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
	 * Loads creatures
	 *
	 * @return list of all creatures.
	 */
	public  HashMap<String, SlashAction> load() {
		final GroupsXMLLoader groupsLoader = new GroupsXMLLoader(uri);
		final  HashMap<String, SlashAction> actions = new  HashMap<String, SlashAction>();
		try {
			List<URI> groups = groupsLoader.load();

			// Load each group
			for (final URI tempUri : groups) {
				final ActionXMLLoader loader = new ActionXMLLoader();

				try {
					actions.putAll(loader.load(tempUri));
				} catch (final SAXException ex) {
					LOGGER.error("Error loading creature group: " + tempUri, ex);
				}
			}
		} catch (SAXException e) {
			LOGGER.error(e, e);
		} catch (IOException e) {
			LOGGER.error(e, e);
		}
		return actions;
	}
}
