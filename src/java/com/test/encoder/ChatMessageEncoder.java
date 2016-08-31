/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.encoder;

import com.test.message.ChatMessage;
import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import org.jboss.logging.Logger;

/**
 *
 * @author Lab
 */
public class ChatMessageEncoder implements Encoder.Text<ChatMessage>{
    private Logger log = Logger.getLogger(getClass().getName());
    @Override
    public String encode(final ChatMessage chatMessage) throws EncodeException {
        log.info("message : " + chatMessage.getMessage());
        return Json.createObjectBuilder()
            .add("message", chatMessage.getMessage())
            .add("sender", chatMessage.getSender())
            .add("received", chatMessage.getReceivedD().toString()).build()
            .toString();
    }

    @Override
    public void init(final EndpointConfig config) {
      
    }

    @Override
    public void destroy() {
     
    }
    
}
