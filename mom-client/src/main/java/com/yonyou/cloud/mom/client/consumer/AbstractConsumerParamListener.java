package com.yonyou.cloud.mom.client.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;

import com.rabbitmq.client.Channel;

/**
 * 一致性的监听者 业务处理的消费者集成该类
 * handle方法增加多个参数
 * 
 * @author BENJAMIN
 *
 */
public abstract class AbstractConsumerParamListener<Data extends Object> implements ChannelAwareMessageListener {

	protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractConsumerJustListen.class);

	@Autowired
	MessageConverter messageConverter;

	/**
	 * 接收到消息 进行处理
	 * @throws Exception 
	 */
	@Override
	@MomConsumer
	public void onMessage (Message message, Channel channel) throws Exception {
		try {

			Object object = messageConverter.fromMessage(message);
			LOGGER.debug("msg content is  " + object);
			handleMessage((Data) object,message.getMessageProperties());

		} catch (MessageConversionException e ) {

			LOGGER.error(e.toString(), e );
		}
	}

	/**
	 * 业务放实现重写该访问，获取自己需要的对象
	 * 
	 * @param data
	 */
	public  abstract void handleMessage(Object... data)  throws Exception;

}
