package com.yonyou.cloud.mom.core.store.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.cloud.mom.core.dto.ProducerDto;
import com.yonyou.cloud.mom.core.store.ProducerMsgStore;
import com.yonyou.cloud.mom.core.store.callback.ProducerStoreDBCallback;
import com.yonyou.cloud.mom.core.store.callback.exception.StoreException;
import com.yonyou.cloud.mom.core.util.SpringUtil;

/**
 * 存储生产者消息的实现
 * 通过DB存储
 */
@Component
public class DbStoreProducerMsg implements ProducerMsgStore {

    private static final Logger LOGGER = LoggerFactory.getLogger(DbStoreProducerMsg.class);

    /**
     * 存储消息
     */
    @Override
    public void msgStore(String msgKey, String data, String exchange, String routerKey,String bizClassName) throws StoreException {

    	ProducerStoreDBCallback producerStoreDBCallback = getCallBack();
    	
        if (producerStoreDBCallback != null && data != null && msgKey != null) {
            LOGGER.debug("save msg to db.");
    	   producerStoreDBCallback.saveMsgData(msgKey, data, exchange, routerKey,bizClassName);

        } else {

            String errorMsg = "";
            if (producerStoreDBCallback == null) {
                errorMsg = "producerStoreDBCallback is null";
            } else if (data == null) {
                errorMsg = "data is null";
            } else {
                errorMsg = "msgKey is null";
            }

            LOGGER.error("msgKey is null");
            throw new StoreException(errorMsg);
        }
    }

    /**
     * 处理为失败
     *
     * @param msgKey
     * @param infoMsg
     * @param costTime
     *
     * @throws StoreException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor =RuntimeException.class)
    public void msgStoreFailed(String msgKey, String infoMsg, Long costTime) throws StoreException {

    	ProducerStoreDBCallback producerStoreDBCallback = getCallBack();

        if (producerStoreDBCallback != null && msgKey != null) {

            LOGGER.debug("data encounter error: " + infoMsg);
            producerStoreDBCallback.update2faild(msgKey, infoMsg, costTime);

        } else {

            String errorMsg = "";
            if (producerStoreDBCallback == null) {
                errorMsg = "dbStoreUserCallback is null";
            } else {
                errorMsg = "msgKey is null";
            }

            LOGGER.error("msgKey is null");
            throw new StoreException(errorMsg);
        }
        
    }
    
    
    /**
     * 处理为失败
     *
     * @param msgKey
     * @param infoMsg
     * @param costTime
     *
     * @throws StoreException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor =RuntimeException.class)
    public void update2success(String msgKey) throws StoreException {

    	ProducerStoreDBCallback producerStoreDBCallback = getCallBack();

        if (producerStoreDBCallback != null && msgKey != null) {

            LOGGER.debug("data encounter error: " + msgKey);
            producerStoreDBCallback.update2success(msgKey);

        } else {
            String errorMsg = "";
            if (producerStoreDBCallback == null) {
                errorMsg = "dbStoreUserCallback is null";
            } else {
                errorMsg = "msgKey is null";
            }
            LOGGER.error("msgKey is null");
            throw new StoreException(errorMsg);
        }
        
    }
    
    /**
     * 获取callback
     * 
     * @return
     */
    private ProducerStoreDBCallback getCallBack(){
    	ProducerStoreDBCallback callback = (ProducerStoreDBCallback) SpringUtil.getBean(ProducerStoreDBCallback.class);
    	return callback;
    }
    
    
    @Override
    public List<ProducerDto> selectResendList(Integer status){
    	ProducerStoreDBCallback producerStoreDBCallback = getCallBack();
    	return producerStoreDBCallback.selectResendList(status);
    }
}
