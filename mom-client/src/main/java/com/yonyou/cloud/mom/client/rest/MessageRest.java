package com.yonyou.cloud.mom.client.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.yonyou.cloud.mom.core.store.ConsumerMsgStore;
import com.yonyou.cloud.mom.core.store.ProducerMsgStore;
import com.yonyou.cloud.mom.client.consumer.ReConsumerDefault;
import com.yonyou.cloud.mom.client.producer.MqSender;

@RestController
@RequestMapping("/msg")
public class MessageRest {
	protected final static Logger logger = LoggerFactory.getLogger(MessageRest.class);

	@Autowired(required = false)
	private ProducerMsgStore msgStore;

	@Autowired(required = false)
	private ConsumerMsgStore consumerMsgStore;

	@Autowired
	private MqSender mqSender;

	@Autowired
	private ReConsumerDefault reConsumer;

	/**
	 * 
	 * @param msgKey
	 * @param type
	 *            {producer 生产者；consumer 消费者}
	 * @return
	 */
	@RequestMapping("/reset/{type}/{msgKey}")
	public boolean reset(@PathVariable("msgKey") String msgKey, @PathVariable("type") String type) {
		if (type.equals("producer")) {
			logger.info("生产者重置发送失败次数" + msgKey);
			msgStore.resetErrorCount(msgKey);
			mqSender.reSendOne(msgKey);
		} else {
			logger.info("消费者重置消费失败次数" + msgKey);
			consumerMsgStore.resetErrorCount(msgKey);
			try {
				reConsumer.reConsumerOne(msgKey);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

}
