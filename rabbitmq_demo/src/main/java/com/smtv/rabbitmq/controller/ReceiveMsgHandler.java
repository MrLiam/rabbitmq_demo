package com.smtv.rabbitmq.controller;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

import com.rabbitmq.client.Channel;

public class ReceiveMsgHandler implements ChannelAwareMessageListener{  
	  
    public void handleMessage(String text) {  
        System.out.println("Received: " + text);  
    }

	@Override
	public void onMessage(Message paramMessage, Channel paramChannel) throws Exception {
		System.out.println("Received: " + new String(paramMessage.getBody()));
		
	}  
} 
