/* $Id$ */
/***************************************************************************
 *                   (C) Copyright 2003-2010 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.core.rule.defaultruleset;

import java.lang.reflect.Constructor;

import org.apache.log4j.Logger;

import games.stendhal.client.ClientSingletonRepository;
import games.stendhal.client.actions.SlashAction;
import games.stendhal.common.constants.Nature;
import games.stendhal.server.core.rule.EntityManager;
import games.stendhal.server.core.rule.defaultruleset.creator.AbstractCreator;
//import games.stendhal.server.core.rule.defaultruleset.creator.FullActionCreator;
import marauroa.common.game.RPAction;
/**
 * Spell information are loaded from XML into a {@link DefaultAction}.
 * The {@link EntityManager} uses this information to instantiate objects of
 * the right type.
 *
 * @author madmetzger
 */
public class DefaultAction  {

	private static final Logger logger = Logger.getLogger(DefaultAction.class);

	private AbstractCreator<SlashAction> creator;

	private String name;

	private Class<?> implementationClass;

	private String clazz;
	
	private String type;
	
	private boolean attributes;

	private String maxParam;

	private String minParam;

	private String target;

	private String zone;

	private String x;

	private String y;
	

	/**
	 * Creates a new {@link DefaultAction}
	 * @param name the name of that spell
	 * @param clazzName class name
	 */
	public DefaultAction(String name, String clazz, final String[] params) {
		final RPAction teleport = new RPAction();

		teleport.put("type", "teleport");
		teleport.put("target", params[0]);
		teleport.put("zone", params[1]);
		teleport.put("x", params[2]);
		teleport.put("y", params[3]);
		

		ClientSingletonRepository.getClientFramework().send(teleport);

	}

	private void buildCreator(final Class< ? > implementation) {
		try {
			Constructor< ? > construct;
			construct = implementation.getConstructor(new Class[] {
					String.class, Nature.class, int.class, int.class, int.class,
					int.class, double.class, int.class, int.class,
					int.class, int.class, int.class, double.class});

		} catch (final NoSuchMethodException ex) {
			logger.error("No matching full constructor for Spell found.", ex);
		}

	}

	/**
	 * Creates a new instance using the configured implementation class of that spell
	 *
	 * @return an instance of the specified implementation class
	 */
	

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the class object specified in the xml configuration
	 */
	public Class<?> getImplementationClass() {
		return implementationClass;
	}
	/**
	 * @return the mana
	 */
	
}
