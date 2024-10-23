package org.example.springbootredisactivemq.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class UserMessageListener {

    @JmsListener(destination = "userQueue")
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
    }
}