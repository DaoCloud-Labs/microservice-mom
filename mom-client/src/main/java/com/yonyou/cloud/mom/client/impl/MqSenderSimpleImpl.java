package com.yonyou.cloud.mom.client.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitGatewaySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.yonyou.cloud.mom.client.MqSender;
import com.yonyou.cloud.track.Track;

/**
 * 发送mq的默认实现
 * 
 * @author BENJAMIN
 *
 */
public class MqSenderSimpleImpl extends RabbitGatewaySupport implements MqSender {

	protected final Logger LOGGER = LoggerFactory.getLogger(MqSenderSimpleImpl.class);

	@Autowired
	private Track tack;
	@Value("${track.isTacks:false}")
	private Boolean isTacks;

	@Override
	public void justSend(String exchange, String routeKey, Object data) {
		try {
			if (isTacks) {
				// 消息主键
				StringBuffer msgKey = new StringBuffer();
				msgKey.append(UUID.randomUUID().toString());  
				Map<String, Object> properties=new HashMap<>();
				properties.put("type", "PRODUCER");
				properties.put("msgKey", msgKey.toString()); 
				properties.put("sender", data.getClass().getName()); 
				properties.put("exchangeName",exchange);
				properties.put("routingKey", routeKey); 
				properties.put("data", data); 
				properties.put("success", "true"); 
				properties.put("host", "localhost"); 
				tack.track("msginit", "mqTrack", properties);
				tack.shutdown();
				LOGGER.info("埋点msgProducer 成功:key=" + msgKey.toString() + ",data=" + data);
			}
		} catch (Exception e1) {
			LOGGER.info("埋点msgProducer 发生异常", e1);
		}
		sendRabbitQ(exchange, routeKey, "", data);
	}

	protected void sendRabbitQ(String exchange, String routeKey, String correlation, Object data) {

		getRabbitOperations().convertAndSend(exchange, routeKey, data, new MessagePostProcessor() {

			@Override
			public Message postProcessMessage(Message message) throws AmqpException {

				try {
					// message.getMessageProperties().setCorrelationIdString(correlation);

					message.getMessageProperties().setCorrelationId(correlation.getBytes());
					message.getMessageProperties().setContentType("json");

				} catch (Exception e) {
					throw new AmqpException(e);
				}

				return message;

			}
		});

		LOGGER.debug("------消息发送完成------");
	}

	@Override
	public void send(String exchange, String routeKey, Object data, String... bizCodes) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resend() {
		// TODO Auto-generated method stub

	}

	public Track getTack() {
		return tack;
	}

	public void setTack(Track tack) {
		this.tack = tack;
	}

}
