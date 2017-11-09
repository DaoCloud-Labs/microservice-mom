package com.yonyou.cloud.mom.demo.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.yonyou.cloud.mom.client.impl.MqSenderDefaultImpl;
import com.yonyou.cloud.mom.core.store.ProducerMsgStore;
import com.yonyou.cloud.mom.core.store.impl.DbStoreProducerMsg;
import com.yonyou.cloud.mom.core.util.SpringUtil;
import com.yonyou.cloud.mom.demo.msg.listener.PointsListenLogin;

@Configuration 
@ComponentScan(basePackages="com.yonyou.cloud.*")
public class MqConfig {
	
	@Value("${spring.rabbitmq.host}")  
	public  String host;   
	
	@Value("${spring.rabbitmq.port}")  
	public  int port;   
	

//	@Bean
//	public MqSenderDefaultImpl mqSenderDefaultImpl(CachingConnectionFactory f, MessageConverter converter) {
//		MqSenderDefaultImpl mqSenderDefaultImpl = new MqSenderDefaultImpl();
//		RabbitTemplate rabbitTemplate = new RabbitTemplate();
//		rabbitTemplate.setConnectionFactory(f);
//		rabbitTemplate.setMessageConverter(converter);
//		mqSenderDefaultImpl.setRabbitTemplate(rabbitTemplate);
////		mqSenderDefaultImpl.setRabbitOperations(rabbitOperations);
//		return mqSenderDefaultImpl;
//	}

	@Bean
	public SpringUtil springUtil() {
		return new SpringUtil();
	}
//
//	@Bean
//	public ProducerMsgStore getDbStoreProducerMsg() {
//		return new DbStoreProducerMsg();
//	}

//	@Bean
//	public Queue pointsListenLoginQueue() {
//		return new Queue("points-login", true); // 队列持久
//	}
//	
//	@Bean
//	public DirectExchange eventExchange() {  
//        return new DirectExchange("event-exchange");
//    }  
//	
//	@Bean
//	public Binding PointsBindingLogin(){
//		return BindingBuilder.bind(pointsListenLoginQueue()).to(eventExchange()).with("login");
//	}
	

//	@Bean
//	public SimpleMessageListenerContainer messageContainer1(CachingConnectionFactory f) {
//		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(f);
//		container.setQueues(pointsListenLoginQueue());
//		container.setExposeListenerChannel(true);
//		container.setMaxConcurrentConsumers(5);
//		container.setConcurrentConsumers(1);
//		container.setAcknowledgeMode(AcknowledgeMode.MANUAL); // 设置确认模式手工确认
//		container.setMessageListener(pointsListenLogin());
//		return container;
//	}
	
//	@Bean
//	public CachingConnectionFactory cachingConnectionFactory(){
//		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(host, port);
//		cachingConnectionFactory.setUsername("test");
//		cachingConnectionFactory.setPassword("test");
//		return cachingConnectionFactory;
//	}
	
//	@Bean
//	public MessageConverter messageConverter(){
//		JsonMessageConverter jsonMessageConverter = new JsonMessageConverter();
//		return jsonMessageConverter;
//	}
	
	
//	@Bean
//	public PointsListenLogin pointsListenLogin(){
//		return new PointsListenLogin();
//	}
//	
}
