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
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import games.stendhal.client.actions.DefaultAction;


public class ActionXMLLoader extends DefaultHandler {
	private String type;
	private int minParameters;
	private int maxParameters;
	private LinkedHashMap<String, String> Fixed;
	private LinkedHashMap<String, Integer> arg1;
	private String arg2;
	
	private static LinkedHashMap<String, DefaultAction> actionList;
	private static final Logger LOGGER = Logger.getLogger(ActionXMLLoader.class);
	private static ActionXMLLoader loader;
	private String filePath;

	public ActionXMLLoader(String filePath) {
		super();
		this.filePath = filePath;
		loader = this;
	}

	public static ActionXMLLoader get() {
		if (loader == null) {
			loader = new ActionXMLLoader("/data/conf/actions.xml");
		}
		return loader;
	}

	public void init() {
		final InputStream instream = getClass().getResourceAsStream(filePath);
		actionList = new LinkedHashMap<String, DefaultAction>();
		if (instream == null) {
			LOGGER.info("Error (" + filePath + ") not found");
			return;
		}
		try {
			load(new URI(filePath));
			instream.close();
		} catch (final SAXException | URISyntaxException | IOException e) {
			LOGGER.error(e);
		}
	}
	public void load(final URI uri) throws SAXException {
		final SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			final SAXParser saxParser = factory.newSAXParser();

			final InputStream is = ActionXMLLoader.class.getResourceAsStream(uri.getPath());

			if (is == null) {
				throw new FileNotFoundException("cannot find resource '" + uri
						+ "' in classpath");
			}
			try {
				saxParser.parse(is, this);
			} finally {
				is.close();
			}
		} catch (final ParserConfigurationException t) {
			LOGGER.error(t);
		} catch (final IOException e) {
			LOGGER.error(e);
			throw new SAXException(e);
		}
	}

	@Override
	public void startElement(final String namespaceURI, final String lName, final String qName,
			final Attributes attrs) {
		if (qName.equals("Q4:action")) {
			type = attrs.getValue("type");
			minParameters = Integer.parseInt(attrs.getValue("MinimumParameters"));
			maxParameters = Integer.parseInt(attrs.getValue("MaximumParameters"));
			Fixed =  new LinkedHashMap<String, String>();
			arg1 =  new LinkedHashMap<String, Integer>();
			arg2 = null;
			
		} else if (qName.equals("Q4:actionAttributes")) {
			String category = attrs.getValue("category");
			String id = attrs.getValue("id");
			switch(category) {
				case "fixed":
					String val = attrs.getValue("val");
					Fixed.put(id, val);
					break;
				case "arg1":
					int num = Integer.parseInt(attrs.getValue("num"));
					arg1.put(id,  num);
					break;
				default:
					arg2 = id;
			}
		}
	}

	@Override
	public void endElement(final String namespaceURI, final String sName, final String qName) {
		if (qName.equals("Q4:action")) {
			DefaultAction action = new DefaultAction(type, minParameters, maxParameters, Fixed, arg1, arg2);
			actionList.put(type, action);
		}
	}

	static public DefaultAction getSlashAction(String type) {
		return actionList.get(type);
	}

}
