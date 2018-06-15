package com.smtv.rabbitmq.config;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;

import com.rabbitmq.client.AMQP;

@Configuration
public class ProducerConfiguration {

	// 指定队列名称 routingkey的名称默认为Queue的名称，使用Exchange类型为DirectExchange
	private String helloWorldQueueName = "rabbitmq_queue_ldl";
	
    private String host = "";  
  
    private String port="5672";  
  
    private String username="liam";  
  
    private String password="liam";  
  
    private Boolean publisherConfirms = true;  
  
    private String virtualHost = "liam_host"; 
    public static final String EXCHANGE   = "spring-mvc-exchange"; 

	// 创建链接
	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host);
		connectionFactory.setUsername(username);
		connectionFactory.setPassword(password);
		connectionFactory.setPort(AMQP.PROTOCOL.PORT);
		return connectionFactory;
	}

	// 创建rabbitTemplate 消息模板类
	@Bean
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate template = new RabbitTemplate(connectionFactory());
		template.setRoutingKey("why_not_going");
		template.setQueue(helloWorldQueueName);
		return template;
	}

	//创建一个调度
	@Bean
	public ScheduledProducer scheduledProducer() {
		return new ScheduledProducer();
	}

	@Bean
	public BeanPostProcessor postProcessor() {
		return new ScheduledAnnotationBeanPostProcessor();
	}

	@Bean  
    public DirectExchange defaultExchange() {  
        return new DirectExchange(EXCHANGE);  
    } 
	
	// Every queue is bound to the default direct exchange
	public Queue helloWorldQueue() {
		return new Queue(this.helloWorldQueueName);
	}

	@Bean  
    public Binding binding() {  
        return BindingBuilder.bind(helloWorldQueue()).to(defaultExchange()).with("why_not_going");  
    } 
	
	static class ScheduledProducer {

		@Autowired
		private volatile RabbitTemplate rabbitTemplate;

		//自增整数
		private final AtomicInteger counter = new AtomicInteger();
		/**
		 * 每3秒发送一条消息
		 */
		@Scheduled(fixedRate = 10000)
		public void sendMessage() {
			System.out.println("send msg");
			System.out.println(rabbitTemplate.getRoutingKey());
			System.out.println(rabbitTemplate.getConnectionFactory());
			String msg = "Hello World " + counter.incrementAndGet()+"我爱你！";
			rabbitTemplate.convertAndSend(EXCHANGE, "why_not_going", msg);
			//rabbitTemplate.convertAndSend("Hello World " + counter.incrementAndGet());
			System.out.println(rabbitTemplate.getExchange());
			System.out.println(msg);
		}
	}

}

