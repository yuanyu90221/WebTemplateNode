package com.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
@ServerEndpoint(value="/time/{user}")
public class TimeEndPoint {
	private final Logger log = Logger.getLogger(getClass().getName());
	public static HashMap<String, Session> sessionMap = new HashMap<String, Session>();
	
	@OnOpen
	public void open(final Session session, @PathParam("user") final String user){
		 log.info("session openend and bound to user: " + user);
		 session.getUserProperties().put("user", user);
		 sessionMap.put(session.getId(),session);
	}
	
	@OnClose
	public void close(final Session session){
		String user = (String) session.getUserProperties().get("user");
		
		sessionMap.remove(session.getId());
		for (Session s : sessionMap.values()) {
			if (s.isOpen() && user.equals(s.getUserProperties().get("user"))) {
				log.info("user : " + user + " 已離開聊天室");
				try {
					s.getBasicRemote().sendText(user + " 已離開聊天室");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public static Session[] getSessions(){
		return sessionMap.values().toArray(new Session[1]);
	}
	
	static {
		Timer t = new Timer();
		Thread th = new Thread(t);
		th.start();
	}
}
