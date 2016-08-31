package com.websocket;

import java.util.HashSet;
import java.util.Set;

import javax.websocket.Endpoint;
import javax.websocket.server.ServerApplicationConfig;
import javax.websocket.server.ServerEndpointConfig;

public class WebsocketConfig implements ServerApplicationConfig {

	@Override
	public Set<Class<?>> getAnnotatedEndpointClasses(Set<Class<?>> scanned) {
		System.out.println("******getAnnotatedEndpointClasses******");
		
		// web application. Filter out all others to avoid issues when running
        // tests on Gump
		//This is mainly to scan type bag, if the prefix is " com.websocket." he seized her, and then do what, you'll see
		Set<Class<?>> res=new HashSet<>();
		for(Class<?> cs:scanned){
			//System.out.println(cs.getName());
			if(cs.getPackage().getName().startsWith("com.websocket")){
				System.out.println(cs.getName());
				res.add(cs);
			}
		}
		return res;
	}

	@Override
	public Set<ServerEndpointConfig> getEndpointConfigs(Set<Class<? extends Endpoint>> scanned) {
		// TODO Auto-generated method stub
		System.out.println("******getEndpointConfigs******");
		Set<ServerEndpointConfig> res=new HashSet<>();
//		for(Class<?> cs:scanned){
//			System.out.println(cs.getName());
//		}

//		Timer t = new Timer();
//		Thread th = new Thread(t);
//		th.start();
		return res;
	}

}
