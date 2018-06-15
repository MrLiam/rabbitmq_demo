package com.smtv.rabbitmq;

import java.io.UnsupportedEncodingException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;  
  
public class QueueListenter implements MessageListener{  

	@Override
	public void onMessage(Message paramMessage) {
		/*byte[] body = paramMessage.getBody();  
        System.out.println("receive msg : " + new String(body)); */   
        try {
			System.out.println("receive msg: "+new String(paramMessage.getBody(),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}  
  
} 
