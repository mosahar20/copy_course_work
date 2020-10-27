/***************************************************************************
 *                   (C) Copyright 2003-2015 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package conf;


import static org.junit.Assert.assertEquals;

import java.io.IOException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



public class TestDwarf {
	//private final transient List<PortalTestObject> portals = new LinkedList<PortalTestObject>();

	@Test
	public void testread() {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse("data/conf/creatures/dwarf.xml");
			NodeList list = doc.getElementsByTagName("creature");
			for(int i=0; i <list.getLength();i++) {
				Node p = list.item(i);
				Element creature = (Element) p;
				//NodeList artibutes = creature.getChildNodes();
				Node O = creature.getElementsByTagName("level").item(0);
				//System.out.println(creature.getElementsByTagName("level") + "hi");
				String Value = O.getAttributes().item(0).getNodeValue();
				//System.out.println(Value + "hi");
				
				if (Integer.parseInt(Value) < 30) {
					
					Node S = creature.getElementsByTagName("attributes").item(0);
					Element Speed = (Element) S;
					Node Sp = Speed.getElementsByTagName("speed").item(0);
					String ValueSpeed = Sp.getAttributes().item(0).getNodeValue();
					//System.out.println(ValueSpeed + "hi");
					//int Speed = Integer.parseInt(Value1);
					assertEquals(0.8,Double.parseDouble(ValueSpeed), 0.001);
					
				}

			}
				
				
				
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			 

		

	

	}
	}
