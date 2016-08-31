package com.websocket;

import java.util.logging.Logger;

import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class ChatMessageEncoder implements Encoder.Text<ChatMessage> {
	private Logger log = Logger.getLogger(getClass().getName());

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(EndpointConfig arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public String encode(ChatMessage textMessage) throws EncodeException {
		log.info("message : " + textMessage.getMessage());
        return Json.createObjectBuilder()
            .add("message", textMessage.getMessage())
            .add("sender", textMessage.getSender())
            .add("received", textMessage.getReceivedD().toString()).build()
            .toString();
	}

}
