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
import com.yonyou.cloud.mom.client.config.AddressConfig;
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
	
	@Value("${track.isTacks:false}")
	private Boolean isTacks; 
	
	@Autowired
	AddressConfig address;
	
    private ConsumerMsgStore dbStoreConsumerMsg  = new DbStoreConsumerMsg();
    
    
    public ConsumerAspect() {
		super();
		LOGGER.debug("MQ  Consumer aop初始化");
	}

    @Around("@annotation(com.yonyou.cloud.mom.client.consumer.MomConsumer)")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
    	// 参数pjp.getArgs()
    	Object[] args = pjp.getArgs();
    	
    	Message message = (Message)args[0];
    	
    	Channel channel = (Channel)args[1];

        boolean exist = false;

        Object object = null;
        //开始时间
        Long startTime = System.currentTimeMillis(); 
        
        try {

            String msgKey = new String ( message.getMessageProperties().getCorrelationId());
            try {
                object = messageConverter.fromMessage(message);
                // 是否存在
                LOGGER.debug("msg data  ==== " +object);
              //false 没有存在，true 已经存在
                exist = dbStoreConsumerMsg.exist(msgKey);

            } catch (MessageConversionException e) {

                LOGGER.error(e.toString(), e);
            }

            if (object != null) {
                if (!exist) {
                	
                	ObjectMapper mapper = new ObjectMapper();
                	String dataConvert = mapper.writeValueAsString(object);
                	String bizclassName=message.getMessageProperties().getHeaders().get("__TypeId__").toString();
                	
             		String consumerClassName=pjp.getTarget().getClass().getName();
             		
                    // setting to processing
                	dbStoreConsumerMsg.saveMsgData(msgKey, dataConvert,message.getMessageProperties().getReceivedExchange(),message.getMessageProperties().getConsumerQueue(),consumerClassName,bizclassName);

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
							properties.put("type", "CONSUMER");
							properties.put("msgKey", msgKey); 
							properties.put("sender", bizclassName); 
							properties.put("exchangeName",message.getMessageProperties().getReceivedExchange());
							properties.put("routingKey", message.getMessageProperties().getConsumerQueue()!=null? message.getMessageProperties().getConsumerQueue():""); 
							properties.put("data", dataConvert);
							properties.put("consumerId", consumerClassName); 
							properties.put("success", "true"); 
							properties.put("host", address.applicationAndHost().get("hostIpAndPro"));
							properties.put("serviceUrl",address.applicationAndHost().get("applicationAddress"));
							tack.track("msgCustomer", "msgCustomer", properties);
							tack.shutdown();
                    		}
						} catch (Exception e1) {
							LOGGER.error("埋点msgCustomer 发生异常",e1);
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
								properties.put("type", "CONSUMER");
								properties.put("msgKey", msgKey); 
								properties.put("sender", bizclassName); 
								properties.put("exchangeName",message.getMessageProperties().getReceivedExchange());
								properties.put("routingKey", message.getMessageProperties().getConsumerQueue()!=null? message.getMessageProperties().getConsumerQueue():""); 
								properties.put("data", dataConvert);
								properties.put("consumerId", consumerClassName); 
								properties.put("success", "false"); 
								properties.put("host", address.applicationAndHost().get("hostIpAndPro"));
								properties.put("serviceUrl",address.applicationAndHost().get("applicationAddress"));
								properties.put("infoMsg", t.getMessage());
								tack.track("msgCustomer", "msgCustomer", properties);
								tack.shutdown();
							}
						} catch (Exception e1) {
							LOGGER.error("埋点msgCustomer 发生异常",e1);
						}
    					
                        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                        throw t;
                    }

                    return rtnOb;
                } else {
                	 channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
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
