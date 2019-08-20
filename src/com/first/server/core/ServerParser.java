package com.first.server.core;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class ServerParser {
	public static int getPort() {
		int port=8080;
		SAXReader saxReader=new SAXReader();
		try {
			Document document=saxReader.read("conf/server.xml");
			Element element=(Element) document.selectSingleNode("//connector");
			port=Integer.parseInt(element.attributeValue("port"));
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return port;
	}

}
