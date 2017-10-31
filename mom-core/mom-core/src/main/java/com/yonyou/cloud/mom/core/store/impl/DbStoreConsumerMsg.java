package com.yonyou.cloud.mom.core.store.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonyou.cloud.mom.core.store.ConsumerMsgStore;
import com.yonyou.cloud.mom.core.store.callback.ConsumerStoreDbCallback;
import com.yonyou.cloud.mom.core.store.callback.exception.StoreException;
import com.yonyou.cloud.mom.core.util.SpringUtil;

/**
 * db存储消费消息的实现
 * 
 * @author BENJAMIN
 *
 */
public class DbStoreConsumerMsg implements ConsumerMsgStore {

    private static final Logger LOGGER = LoggerFactory.getLogger(DbStoreConsumerMsg.class);

 
    private ConsumerStoreDbCallback getCallBack(){
    	ConsumerStoreDbCallback callback = (ConsumerStoreDbCallback) SpringUtil.getBean(ConsumerStoreDbCallback.class);
    	return callback;
    }


	@Override
	public boolean exist(String msgKey) throws StoreException {
		ConsumerStoreDbCallback callback = getCallBack();
		boolean isExist = callback.exist(msgKey);
        return isExist;
	}


	@Override
	public boolean isProcessing(String msgKey) throws StoreException {
		ConsumerStoreDbCallback callback = getCallBack();
		boolean isProcessing = callback.isProcessing(msgKey);
        return isProcessing;
		
	}


	@Override
	public void updateMsgProcessing(String msgKey,String data,String exchange,String queue,String bizClassName) throws StoreException {
		ConsumerStoreDbCallback callback = getCallBack();
		callback.updateMsgProcessing(msgKey,data,exchange,queue,bizClassName);
	}


	@Override
	public void updateMsgSuccess(String msgKey) throws StoreException {
		ConsumerStoreDbCallback callback = getCallBack();
		callback.updateMsgSuccess(msgKey);
		
	}


	@Override
	public void updateMsgFaild(String msgKey) throws StoreException {
		ConsumerStoreDbCallback callback = getCallBack();
		callback.updateMsgFaild(msgKey);
		
	}
}
