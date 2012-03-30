package com.wcs.common.controller;

import java.io.Serializable;

import javax.enterprise.context.Conversation;
import javax.inject.Inject;

public class PerimissionsConversationManager implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private Conversation conversation;
	
	public void conversationDestroy() {
		conversation.end();

	}

	public void initConversation() {
		if (conversation.isTransient()) {
			conversation.begin();
		}
	}
}
