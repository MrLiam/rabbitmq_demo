package com.smtv.rabbitmq.controller;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.smtv.rabbitmq.config.ProducerConfiguration;  

public class ProducerMain {  
  
    public static void main(String[] args) {  
    	 new AnnotationConfigApplicationContext(ProducerConfiguration.class); 
    }  
}  
