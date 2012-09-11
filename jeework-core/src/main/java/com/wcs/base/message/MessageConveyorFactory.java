package com.wcs.base.message;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;

/**
 * @author Chris Guan
 */
public class MessageConveyorFactory {

	private static MessageConveyorFactory INSTANCE;

	private MessageConveyorFactory() {
	}

	private static MessageConveyorFactory load() {
		return new MessageConveyorFactory();
	}

	public static MessageConveyorFactory messageConveyerFactory() {
		if (INSTANCE == null) {
			INSTANCE = load();
		}
		return INSTANCE;
	}

	public static void cleanup() {
		INSTANCE = null;
	}

	public IMessageConveyor getDefaultMessageConveyer() {
		return new MessageConveyor(java.util.Locale.SIMPLIFIED_CHINESE);
	}

	public IMessageConveyor getMessageConveyor(java.util.Locale locale) {
		return new MessageConveyor(locale);
	}

}
