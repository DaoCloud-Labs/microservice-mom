package com.yonyou.cloud.mom.client.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageListener;

/**
 * 非一致性的监听者
 * 极端情况会丢消息
 * 
 * @author BENJAMIN
 *
 */
public abstract class AbstractConsumerJustListen implements MessageListener{
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractConsumerJustListen.class);

}
