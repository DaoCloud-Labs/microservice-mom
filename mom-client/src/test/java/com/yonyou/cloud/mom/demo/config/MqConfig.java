package com.yonyou.cloud.mom.demo.config;


import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitOperations;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.yonyou.cloud.mom.client.impl.MqSenderDefaultImpl;
import com.yonyou.cloud.mom.core.store.ProducerMsgStore;
import com.yonyou.cloud.mom.core.store.impl.DbStoreProducerMsg;
import com.yonyou.cloud.mom.core.util.SpringUtil;
import com.yonyou.cloud.mom.demo.msg.listener.PointsListenLogin;

@Configuration
@ComponentScan(basePackages = "com.yonyou.cloud.mom")
// @PropertySource(value = "classpath:application-dev.properties",
// ignoreResourceNotFound = true)
public class MqConfig {

	@Bean
	public MqSenderDefaultImpl mqSenderDefaultImpl(RabbitOperations rabbitOperations) {
		MqSenderDefaultImpl mqSenderDefaultImpl = new MqSenderDefaultImpl();
		mqSenderDefaultImpl.setRabbitOperations(rabbitOperations);
		return mqSenderDefaultImpl;
	}

	@Bean
	public SpringUtil springUtil() {
		return new SpringUtil();
	}

//	@Bean
//	public ProducerMsgStore getDbStoreProducerMsg() {
//		return new DbStoreProducerMsg();
//	}

	@Bean
	public Queue pointsListenLoginQueue() {
		return new Queue("queue-name", true); // 队列持久
	}

	@Bean
	public DirectExchange eventExchange() {
		return new DirectExchange("exchange-name");
	}

	@Bean
	public Binding PointsBindingLogin() {
		return BindingBuilder.bind(pointsListenLoginQueue()).to(eventExchange()).with("queue-key");
	}

	@Bean
	public SimpleMessageListenerContainer messageContainer1(ConnectionFactory connectionFactory,
			PointsListenLogin pointsListenLogin) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
		container.setQueues(pointsListenLoginQueue());
		container.setExposeListenerChannel(true);
		container.setMaxConcurrentConsumers(1);
		container.setConcurrentConsumers(1);
		container.setAcknowledgeMode(AcknowledgeMode.MANUAL); // 设置确认模式手工确认
		container.setMessageListener(pointsListenLogin);
		container.setMaxConcurrentConsumers(10);//设置最大消费者数量 防止大批量涌入
		return container;
	}

	@Bean
	public MessageConverter messageConverter() {
		JsonMessageConverter jsonMessageConverter = new JsonMessageConverter();
		return jsonMessageConverter;
	}
 
}
