package com.websocket;

import java.io.StringReader;
import java.util.Date;
import java.util.logging.Logger;

import javax.json.JsonObject;
import javax.json.Json;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;


public class ChatMessageDecoder implements Decoder.Text<ChatMessage> {
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
	public ChatMessage decode(String textMessage) throws DecodeException {
		ChatMessage chatMessage = new ChatMessage();
        JsonObject obj = Json.createReader(new StringReader(textMessage)).readObject();
        log.info("message : " + obj.getString("message"));
        chatMessage.setMessage(obj.getString("message"));
        chatMessage.setSender(obj.getString("sender"));
        chatMessage.setReceivedD(new Date());
        return chatMessage;
	}

	@Override
	public boolean willDecode(String arg0) {
		// TODO Auto-generated method stub
		return true;
	}

}
