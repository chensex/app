package com.app.listener;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

public class TestListener implements MessageListener{

	@Override
	public void onMessage(Message message) {
		MapMessage mapMessage = (MapMessage) message;
		try {
			System.out.println(mapMessage.getObject("test"));
		} catch (Exception ee) {
			ee.printStackTrace();
		}
	}

}
