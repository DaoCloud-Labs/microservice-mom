package com.yonyou.cloud.mom.core.store.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonyou.cloud.mom.core.store.ConsumerMsgStore;
import com.yonyou.cloud.mom.core.store.callback.ProducerStoreDBCallback;
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

 
    private ProducerStoreDBCallback getCallBack(){
    	ProducerStoreDBCallback callback = (ProducerStoreDBCallback) SpringUtil.getBean(ProducerStoreDBCallback.class);
    	return callback;
    }


	@Override
	public boolean exist(String msgKey) throws StoreException {
		// TODO Auto-generated method stubre
		return false;
	}


	@Override
	public boolean isProcessing(String msgKey) throws StoreException {
		// TODO Auto-generated method stub
		return false;
		
	}


	@Override
	public void updateMsgProcessing(String msgKey) throws StoreException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateMsgSuccess(String msgKey) throws StoreException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateMsgFaild(String msgKey) throws StoreException {
		// TODO Auto-generated method stub
		
	}
}
