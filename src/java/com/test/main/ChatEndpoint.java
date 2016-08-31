/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.main;

import com.test.decoder.ChatMessageDecoder;
import com.test.encoder.ChatMessageEncoder;
import com.test.message.ChatMessage;
import java.io.IOException;
import java.util.logging.Level;
import javax.websocket.EncodeException;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.OnClose;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import org.jboss.logging.Logger;

/**
 *
 * @author Lab
 */
@ServerEndpoint(value="/chat/{room}", encoders = ChatMessageEncoder.class,decoders = ChatMessageDecoder.class )
public class ChatEndpoint {
    private final Logger log = Logger.getLogger(getClass().getName());
    
    @OnOpen
    public void open(final Session session, @PathParam("room") final String room){
        log.info("session openend and bound to room: " + room);
	session.getUserProperties().put("room", room);
    }
    
    @OnMessage
    public void onMessage(final Session session, final ChatMessage chatMessage){
        String room = (String) session.getUserProperties().get("room");
		try {
			for (Session s : session.getOpenSessions()) {
				if (s.isOpen() && room.equals(s.getUserProperties().get("room"))) {
                                        log.info("message : " + chatMessage.getMessage());
					s.getBasicRemote().sendObject(chatMessage);
				}
			}
		} catch (IOException | EncodeException e) {
			log.log(Logger.Level.WARN, "onMessage failed:", e);
		}
    }
    
    @OnClose
    public void close(final Session session) {
        String room = (String) session.getUserProperties().get("room");
        try{		
            for (Session s : session.getOpenSessions()) {
                if (s.isOpen() && room.equals(s.getUserProperties().get("room"))) {
                    log.info("user : ");
                    s.getBasicRemote().sendText(session.getId() + " 已離開聊天室");
                }
            }
        }
        catch(IOException e){
            log.log(Logger.Level.WARN, "onMessage failed:", e);  
        }
    }
}
