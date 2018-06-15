package com.smtv.rabbitmq.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.smtv.rabbitmq.controller.ReceiveMsgHandler;

@Configuration
public class ConsumerConfiguration {

	// 指定队列名称 routingkey的名称默认为Queue的名称，使用Exchange类型为DirectExchange
	private String springQueueDemo = "rabbitmq_queue_ldl";
	
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
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(
				this.host);
		connectionFactory.setUsername(username);
		connectionFactory.setPassword(password);
		connectionFactory.setPort(AMQP.PROTOCOL.PORT);
		return connectionFactory;
	}

	// 创建rabbitAdmin 代理类
	@Bean
	public AmqpAdmin amqpAdmin() {
		return new RabbitAdmin(connectionFactory());
	}

	// 创建rabbitTemplate 消息模板类
	@Bean
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate template = new RabbitTemplate(connectionFactory());
		// The routing key is set to the name of the queue by the broker for the
		// default exchange.
		template.setRoutingKey("why_not_going");
		// Where we will synchronously receive messages from
		template.setQueue(this.springQueueDemo);
		return template;
	}

	 @Bean  
    public DirectExchange defaultExchange() {  
        return new DirectExchange(EXCHANGE);  
    } 
	
	// Every queue is bound to the default direct exchange
	public Queue helloWorldQueue() {
		return new Queue(this.springQueueDemo);
	}

	@Bean  
    public Binding binding() {  
        return BindingBuilder.bind(helloWorldQueue()).to(defaultExchange()).with("why_not_going");  
    } 
	
	
	@Bean
	public SimpleMessageListenerContainer listenerContainer() {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory());
		container.setQueueNames(this.springQueueDemo);
		container.setMessageListener(new ChannelAwareMessageListener() {
			
			@Override
			public void onMessage(Message paramMessage, Channel paramChannel) throws Exception {
				// TODO Auto-generated method stub
				 byte[] body = paramMessage.getBody();  
	              System.out.println("receive msg : " + new String(body)); 
			}
		});
		System.out.println(container);
		return container;
	}

}

