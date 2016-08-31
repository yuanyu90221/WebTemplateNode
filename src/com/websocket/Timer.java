package com.websocket;

import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Logger;

import javax.websocket.Session;

public class Timer implements Runnable{
	private final Logger log = Logger.getLogger(getClass().getName());
	public static String currentTime="";
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//
//	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			log.info("start");
			Calendar c = Calendar.getInstance();
			String resp = Timer.formatTime(c);
			
			for(Session s : TimeEndPoint.getSessions()){
				try {
					if(currentTime.equals(resp)){
						s.getBasicRemote().sendText(resp);
					}
					currentTime = resp;
					log.info(resp);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public static String formatTime(Calendar c){
		return String.format("%02d:%02d:%02d",c.get(Calendar.HOUR),
				             c.get(Calendar.MINUTE)+1, c.get(Calendar.SECOND));
	}

//	static {
//		Timer t = new Timer();
//		Thread th = new Thread(t);
//		th.start();
//	}
}
