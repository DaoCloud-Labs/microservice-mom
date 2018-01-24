package com.yonyou.cloud.mom.core.store.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.yonyou.cloud.mom.core.dto.ConsumerDto;
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
@Component
public class DbStoreConsumerMsg implements ConsumerMsgStore {

	private static final Logger LOGGER = LoggerFactory.getLogger(DbStoreConsumerMsg.class);

	private ConsumerStoreDbCallback getCallBack() {
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
	public void updateMsgProcessing(String msgKey, String data, String exchange, String queue, String consumerClassName,
			String bizClassName) throws StoreException {
		ConsumerStoreDbCallback callback = getCallBack();
		callback.updateMsgProcessing(msgKey, data, exchange, queue, consumerClassName, bizClassName);
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

	@Override
	public List<ConsumerDto> selectReConsumerList(Integer status) {
		ConsumerStoreDbCallback callback = getCallBack();
		return callback.selectReConsumerList(status);
	}

	/**
	 * 重置消费失败的次数
	 * 
	 * @param msgKey
	 */
	public Boolean resetErrorCount(String msgKey) {
		ConsumerStoreDbCallback callback = getCallBack();
		return callback.resetErrorCount(msgKey);
	}
}
