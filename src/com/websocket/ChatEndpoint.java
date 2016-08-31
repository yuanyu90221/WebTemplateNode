package com.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value="/chat/{room}", encoders= ChatMessageEncoder.class, decoders = ChatMessageDecoder.class)
public class ChatEndpoint {
	private final Logger log = Logger.getLogger(getClass().getName());
    public static HashMap<String, Session> sessionMap = new HashMap<String, Session>();
	
	 @OnOpen
	    public void open(final Session session, @PathParam("room") final String room){
	        log.info("session openend and bound to room: " + room);
	        session.getUserProperties().put("room", room);

	        sessionMap.put(session.getId(), session);
	    }
	    
	    @OnMessage
	    public void onMessage(final Session session, final ChatMessage chatMessage){
	        String room = (String) session.getUserProperties().get("room");
	        try {
				for (Session s :sessionMap.values()) {
					System.out.println(s.getUserProperties().get("room"));
					if (s.isOpen() && room.equals(s.getUserProperties().get("room"))) {
	                                        log.info("message : " + chatMessage.getMessage());
						s.getBasicRemote().sendObject(chatMessage);
					}
				}
			} catch (IOException | EncodeException e) {
			
				log.log(Level.WARNING, "onMessage failed:", e);
			}
	    }
	    
	    @OnClose
	    public void close(final Session session) {
	        String room = (String) session.getUserProperties().get("room");
	        String leaveUser = session.getId();
	        sessionMap.remove(leaveUser);
	
			for (Session s : sessionMap.values()) {
				if (s.isOpen() && room.equals(s.getUserProperties().get("room"))) {
					log.info("user : " + leaveUser + " 已離開聊天室");
					// s.getBasicRemote().sendText(leaveUser + " 已離開聊天室");
				}
			}

	    }
}
