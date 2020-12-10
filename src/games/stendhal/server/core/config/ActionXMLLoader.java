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
package games.stendhal.server.core.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import games.stendhal.client.actions.SlashAction;
//import games.stendhal.server.core.rule.defaultruleset.DefaultAction;
//import marauroa.common.game.RPAction;

public final class ActionXMLLoader extends DefaultHandler {
	/** the logger instance. */
	private static final Logger logger = Logger.getLogger(ActionXMLLoader.class);

	private String name;

	private String clazz;
	
	private String type;

	private String implementation;
	
	private boolean attributes;

	private String maxParam;

	private String minParam;

	private String target;

	private String zone;

	private String x;

	private String y;
	
	private String text;
	


	

	private  HashMap<String, SlashAction> list;


	ActionXMLLoader() {
		// hide constructor, use the CreatureGroupsXMLLoader instead
	}

	public HashMap<String, SlashAction> load(final URI ref) throws SAXException {
		list = new HashMap<String, SlashAction>();
		// Use the default (non-validating) parser
		final SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			// Parse the input
			final SAXParser saxParser = factory.newSAXParser();

			InputStream is = ActionXMLLoader.class.getResourceAsStream(ref.getPath());

			if (is == null) {
				throw new FileNotFoundException("cannot find resource '" + ref
						+ "' in classpath");
			}

			try {
				saxParser.parse(is, this);
			} finally {
				is.close();
			}
		} catch (final ParserConfigurationException t) {
			logger.error(t);
		} catch (final IOException e) {
			logger.error(e);
			throw new SAXException(e);
		}
		return list;
	}

	@Override
	public void startDocument() {
		// do nothing
	}

	@Override
	public void endDocument() {
		// do nothing
	}

	@Override
	public void startElement(final String namespaceURI, final String lName, final String qName,
			final Attributes attrs) {
		text = "";
		if (qName.equals("action")) {
			name = attrs.getValue("name");
			implementation = null;
			attributes = false;
			type = null;
			maxParam = null;
			minParam = null;
			target = null;
			zone = null;
			x = null;
			y = null;
			
		} if (qName.equals("implementation")) {
			implementation = attrs.getValue("value");
		
		} if (qName.equals("attributes")) {
			attributes = true;
		} if (attributes && qName.equals("type")) {
			type = attrs.getValue("value");
		}if (attributes && qName.equals("maxParameters")) {
			maxParam = attrs.getValue("value");
		} if (attributes && qName.equals("minParameters")) {
			minParam = attrs.getValue("value");
		} if (attributes && qName.equals("target")) {
			target = attrs.getValue("value");
		}if (attributes && qName.equals("zone")) {
			zone = attrs.getValue("value");
		}if (attributes && qName.equals("x")) {
			x = attrs.getValue("x");
		}if (attributes && qName.equals("y")) {
			y = attrs.getValue("y");
		}
	
	}

	@Override
	public void endElement(final String namespaceURI, final String sName, final String qName) {
		if (qName.equals("action")) {
			//String [] param = {"mohammed" ,"0_semos_city", "20" ,"20"};
			//final DefaultAction action = new DefaultAction(name,type,param);

			
			//list.put(type, action);
		}
	}
	@Override
	public void characters(final char[] buf, final int offset, final int len) {
		text = text + (new String(buf, offset, len)).trim() + " ";
	}
}
