// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3)
// Source File Name: XmlProperties.java

package com.wcs.base.report.xml;

import java.io.IOException;
import java.io.InputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public abstract class XmlProperties {

	protected Document xmlProperties;

	protected Element root;

	public XmlProperties(String fileName) throws XmlPropertiesException {
		InputStream in = null;
		try {
			in = getClass().getResourceAsStream("/" + fileName);
			if (in == null) {
				in = getClass().getResourceAsStream(fileName);
			}
			SAXBuilder parser = new SAXBuilder();
			xmlProperties = parser.build(in);
			root = xmlProperties.getRootElement();
		} catch (JDOMException je) {
			throw new XmlPropertiesException(
					"Could not read xml properties file: " + fileName, je);
		} catch (IOException ioe) {
			throw new XmlPropertiesException(
					"Could not read xml properties file: " + fileName, ioe);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException ioexception) {
					// Ignore
				}
			}
		}
	}

}