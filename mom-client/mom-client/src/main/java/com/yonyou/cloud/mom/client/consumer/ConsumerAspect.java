package com.yonyou.cloud.mom.client.consumer;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.yonyou.cloud.mom.core.store.ConsumerMsgStore;
import com.yonyou.cloud.mom.core.store.callback.exception.StoreDBCallbackException;
import com.yonyou.cloud.mom.core.store.callback.exception.StoreException;
import com.yonyou.cloud.mom.core.store.impl.DbStoreConsumerMsg;
import com.yonyou.cloud.track.Track;

/**
 * consumer的aop
 * 
 * @author BENJAMIN
 *
 */
@Aspect
@Component
public class ConsumerAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerAspect.class);

	@Autowired
	MessageConverter messageConverter;
	
	@Autowired
	Track tack;
	
	@Value("${track.isTacks}")
	private Boolean isTacks; 
	
    private ConsumerMsgStore dbStoreConsumerMsg  = new DbStoreConsumerMsg();
    
    
    public ConsumerAspect() {
		super();
		LOGGER.debug("MQ  Consumer aop初始化");
	}

    @Around("@annotation(com.yonyou.cloud.mom.client.consumer.MomConsumer)")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
    	
    	Object[] args = pjp.getArgs();// 参数pjp.getArgs()
    	
    	Message message = (Message)args[0];
    	
    	Channel channel = (Channel)args[1];

        boolean isProcessing = false;

        Object object = null;

        Long startTime = System.currentTimeMillis(); //开始时间
        
        try {

            String msgKey = new String ( message.getMessageProperties().getCorrelationId());
            try {
                object = messageConverter.fromMessage(message);
                // 是否在处理中
                LOGGER.debug("msg data  ==== " +object);
                isProcessing = dbStoreConsumerMsg.isProcessing(msgKey);//false 没有在处理中，true已经在出来中了

            } catch (MessageConversionException e) {

                LOGGER.error(e.toString(), e);
            }

            if (object != null) {
                if (!isProcessing) {
                	
                	ObjectMapper mapper = new ObjectMapper();
                	String dataConvert = mapper.writeValueAsString(object);
                	String bizclassName=message.getMessageProperties().getHeaders().get("__TypeId__").toString();
                	
             		String consumerClassName=pjp.getTarget().getClass().getName();
             		
                    // setting to processing
                	dbStoreConsumerMsg.updateMsgProcessing(msgKey, dataConvert,message.getMessageProperties().getReceivedExchange(),message.getMessageProperties().getConsumerQueue(),consumerClassName,bizclassName);

                    // 执行
                    Object rtnOb;
                    try {

                        // 执行方法
                        rtnOb = pjp.proceed();
                        // setting to success
                        dbStoreConsumerMsg.updateMsgSuccess(msgKey);
                    	
                        //消息消费成功埋点 
                    	try {
                    		if(isTacks) {
							Map<String, Object> properties=new HashMap<>();
							properties.put("sender", "消息消费成功");
							properties.put("msgKey", msgKey); 
							properties.put("static", "success");
							properties.put("consumerClassName", consumerClassName);
							properties.put("bizclassName", bizclassName);
							tack.track("msgCustomer", "msgCustomer", properties);
							tack.shutdown();
                    		}
						} catch (Exception e1) {
							LOGGER.info("埋点msgCustomer 发生异常");
						}
    					
                        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                    } catch (Throwable t) {
                        LOGGER.error(t.getMessage());

                        // setting to failed
                        dbStoreConsumerMsg.updateMsgFaild(msgKey);
                        
                        //消息消费失败埋点
						try {
							if(isTacks) {
							Map<String, Object> properties=new HashMap<>();
							properties.put("sender", "消息消费失败");
							properties.put("msgKey", msgKey); 
							properties.put("static", "success");
							properties.put("consumerClassName", consumerClassName);
							properties.put("bizclassName", bizclassName);
							tack.track("msgCustomer", "msgCustomer", properties);
							tack.shutdown();
							}
						} catch (Exception e1) {
							LOGGER.info("埋点msgCustomer 发生异常");
						}
    					
                        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                        throw t;
                    }

                    return rtnOb;
                } else {

                    LOGGER.info("is processing, ignore: " + object.toString());
                }
            }

        } catch (StoreException storeException) {

            LOGGER.error(storeException.toString(), storeException);

        } catch (StoreDBCallbackException storeCallbackException) {

            LOGGER.error("user's store call back error", storeCallbackException);

        } catch (Exception e) {

            LOGGER.error("process message error", e);
        }

        return null;
    }

}
