package com.yonyou.cloud.mom.client.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;

import com.rabbitmq.client.Channel;

/**
 * 非一致性的监听者
 * 极端情况会丢消息
 * 
 * @author BENJAMIN
 *
 */
public abstract class AbstractConsumerJustListen<Data extends Object> implements ChannelAwareMessageListener{
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractConsumerJustListen.class);
	
	@Autowired
	MessageConverter messageConverter;
	
	/**
	 * 接收到消息 进行处理
	 */
	@Override
	public void onMessage(Message message, Channel channel) {
		try {

			Object object = messageConverter.fromMessage(message);
			LOGGER.debug("msg content is  " + object);
			handleMessage((Data) object);

		} catch (MessageConversionException e ) {

			LOGGER.error(e.toString(), e );
		}
	}

	/**
	 * 业务放实现重写该访问，获取自己需要的对象
	 * 
	 * @param data
	 */
	protected abstract void handleMessage(Data data);

}
