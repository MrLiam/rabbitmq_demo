package com.smtv.rabbitmq;

import java.util.UUID;  

import org.springframework.amqp.rabbit.connection.ConnectionFactory;  
import org.springframework.amqp.rabbit.core.RabbitTemplate;  
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;  
import org.springframework.amqp.rabbit.support.CorrelationData;  
import org.springframework.beans.factory.annotation.Autowired;  
  
public class RmqProducer implements ConfirmCallback{  
  
	@Autowired
    private RabbitTemplate rabbitTemplate;  
  
    public void sendMessage(String content) {  
        try {  
        	System.out.println(rabbitTemplate.getConnectionFactory().getHost());
        	System.out.println(rabbitTemplate.getConnectionFactory().getPort());
        	String id = UUID.randomUUID().toString();
            CorrelationData correlationId = new CorrelationData(id);  
            rabbitTemplate.convertAndSend("rabbit_driect_mq_exchange", "rabbitmq_test2",content,correlationId);  
            System.out.println("消息发送成功！" + id);
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  

	@Override
	public void confirm(CorrelationData arg0, boolean arg1, String arg2) {
		// TODO Auto-generated method stub
		System.out.println(" 回调id:" + arg0+"ack:"+arg1+",  "+arg2);   
	}  
}  
