/*
 * Created on Mar 30, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.wcs.base.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Element;

import com.wcs.base.report.xml.XmlProperties;
import com.wcs.base.report.xml.XmlPropertiesException;

/**
 * @author olivieri
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ColumnProperties extends XmlProperties {

	private Map columnMap = new HashMap();

	/**
	 * @param fileName
	 * @throws XmlPropertiesException
	 */
	public ColumnProperties(String fileName) throws XmlPropertiesException {
		super(fileName);
		init();
		// release reference to jdom document
		xmlProperties = null;
		root = null;
	}

	private void init() {
		List columns = root.getChildren();
		int size = columns.size();
		for (int i = 0; i < size; i++) {
			Element column = (Element) columns.get(i);
			Column col = new Column();
			String key = column.getChildTextNormalize("name");
			col.setName(key);
			col.setAlignment(column.getChildTextNormalize("alignment"));
			col.setWidth(Integer
					.parseInt(column.getChildTextNormalize("width")));
			col.setClazzType(column.getChildTextNormalize("clazzType"));
			col.setExpression(column.getChildTextNormalize("expression"));
			columnMap.put(key, col);
		}
	}
	
	private Column getColumn(String name) {
		return (Column) columnMap.get(name);
	}

}