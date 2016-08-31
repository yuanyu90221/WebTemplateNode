package com.websocket;

import java.util.Date;

public class ChatMessage {
	private String message;
    private String sender;
    private Date receivedD;

    public final String getMessage() {
        return message;
    }

    public final void setMessage(String message) {
        this.message = message;
    }

    public final String getSender() {
        return sender;
    }

    public final void setSender(String sender) {
        this.sender = sender;
    }

    public final Date getReceivedD() {
        return receivedD;
    }

    public final void setReceivedD(Date receivedD) {
        this.receivedD = receivedD;
    }

    @Override
    public String toString() {
        return "ChatMessage{" + "message=" + message + ", sender=" + sender + ", receivedD=" + receivedD + '}';
    }
}
