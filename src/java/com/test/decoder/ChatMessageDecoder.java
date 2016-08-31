/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.decoder;
import com.test.message.ChatMessage;
import java.io.StringReader;
import java.util.Date;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import org.jboss.logging.Logger;

/**
 *
 * @author Lab
 */
public class ChatMessageDecoder implements Decoder.Text<ChatMessage>{
    private Logger log = Logger.getLogger(getClass().getName());
    @Override
    public ChatMessage decode(final String textMessage) throws DecodeException {
        ChatMessage chatMessage = new ChatMessage();
        JsonObject obj = Json.createReader(new StringReader(textMessage)).readObject();
        log.info("message : " + obj.getString("message"));
        chatMessage.setMessage(obj.getString("message"));
        chatMessage.setSender(obj.getString("sender"));
        chatMessage.setReceivedD(new Date());
        return chatMessage;
    }

    @Override
    public boolean willDecode(final String arg0) {
       return true;
    }

    @Override
    public void init(EndpointConfig config) {
        
    }

    @Override
    public void destroy() {
        
    }
    
}
