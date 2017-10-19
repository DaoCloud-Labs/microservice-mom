package com.yonyou.cloud.mom.client.consumer;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yonyou.cloud.mom.core.store.callback.exception.StoreDBCallbackException;
import com.yonyou.cloud.mom.core.store.callback.exception.StoreException;
import com.yonyou.cloud.mom.core.store.impl.DbStoreConsumerMsg;

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

    public static final MessageConverter messageConverter = new JsonMessageConverter();

    @Autowired
    private DbStoreConsumerMsg dbStoreConsumerMsg ;

    @Around("@annotation(MomConsumer) && args(message)")
    public Object aroundAdvice(final ProceedingJoinPoint pjp, MomConsumer momConsumer, Message message)
        throws Throwable {

        boolean isProcessing = false;

        Object object = null;

        Long startTime = System.currentTimeMillis();

        try {

            String msgKey = message.getMessageProperties().getCorrelationIdString();
            try {
                object = ConsumerAspect.messageConverter.fromMessage(message);
                // 是否在处理中
                isProcessing = dbStoreConsumerMsg.isProcessing(msgKey);

            } catch (MessageConversionException e) {

                LOGGER.error(e.toString(), e);
            }

            if (object != null) {
                if (!isProcessing) {

                    // setting to processing
                	dbStoreConsumerMsg.updateMsgProcessing(msgKey);

                    // 执行
                    Object rtnOb;
                    try {

                        // 执行方法
                        rtnOb = pjp.proceed();

                        // setting to success
                        dbStoreConsumerMsg.updateMsgSuccess(msgKey);

                    } catch (Throwable t) {
                        LOGGER.error(t.getMessage());

                        // setting to failed
                        dbStoreConsumerMsg.updateMsgFaild(msgKey);

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
