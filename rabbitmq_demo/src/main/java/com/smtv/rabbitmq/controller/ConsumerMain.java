package com.smtv.rabbitmq.controller;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.smtv.rabbitmq.config.ConsumerConfiguration;  

public class ConsumerMain {  
    public static void main(String[] args) {
        ApplicationContext context =  new AnnotationConfigApplicationContext(ConsumerConfiguration.class);  
        //声明一个队列代码如：
        AmqpAdmin amqpAdmin = context.getBean(AmqpAdmin.class);  
        Queue helloWorldQueue = new Queue("rabbitmq_queue_ldl");  
        amqpAdmin.declareQueue(helloWorldQueue);  
    }  
} 