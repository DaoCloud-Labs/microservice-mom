package com.yonyou.cloud.mom.client.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.support.converter.MessageConversionException;

/**
 * 一致性的监听者
 * 业务处理的消费者集成该类
 * 
 * @author BENJAMIN
 *
 */
public abstract class AbstractConsumerListerner<Data extends Object>  implements MessageListener {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractConsumerJustListern.class);

	/**
	 * 接收到消息 进行处理
	 */
	@Override
	@MomConsumer
	public void onMessage(Message message) {
		   try {

	            Object object = ConsumerAspect.messageConverter.fromMessage(message);

	            handleMessage((Data) object);

	        } catch (MessageConversionException e) {

	        	LOGGER.error(e.toString(), e);
	        }
	}
	
    /**
     * 业务放实现重写该访问，获取自己需要的对象
     * 
     * @param data
     */
    protected abstract void handleMessage(Data data);

}
