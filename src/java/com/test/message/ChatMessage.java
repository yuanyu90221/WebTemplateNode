/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.message;

import java.util.Date;

/**
 *
 * @author Lab
 */
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
