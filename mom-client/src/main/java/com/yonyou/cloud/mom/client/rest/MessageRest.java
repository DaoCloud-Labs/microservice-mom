package com.yonyou.cloud.mom.client.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.yonyou.cloud.mom.client.consumer.ReConsumerDefault;
import com.yonyou.cloud.mom.client.producer.MqSender;
/**
 * 
 * @author daniell
 *
 */
@RestController
@RequestMapping("/msg")
public class MessageRest {
	protected final static Logger logger = LoggerFactory.getLogger(MessageRest.class); 

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
		if ("producer".equals(type)) {
			mqSender.resend(msgKey);
		} else {
			try {
				reConsumer.reConsumer(msgKey);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

}
