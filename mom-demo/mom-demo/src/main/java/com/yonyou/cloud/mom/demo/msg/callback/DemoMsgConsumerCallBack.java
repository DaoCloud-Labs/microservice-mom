package com.yonyou.cloud.mom.demo.msg.callback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.cloud.mom.core.dto.ConsumerDto;
import com.yonyou.cloud.mom.core.store.StoreStatusEnum;
import com.yonyou.cloud.mom.core.store.callback.ConsumerStoreDbCallback;
import com.yonyou.cloud.mom.core.store.callback.exception.StoreDBCallbackException;
import com.yonyou.cloud.mom.demo.dao.ConsumerMsgMapper;
import com.yonyou.cloud.mom.demo.entity.ConsumerMsg;

@Service
@Transactional
public class DemoMsgConsumerCallBack implements ConsumerStoreDbCallback{

	@Autowired
	ConsumerMsgMapper consumerMsgMapper;

	@Override
	public boolean exist(String msgKey) throws StoreDBCallbackException {
		ConsumerMsg consumerMsg = consumerMsgMapper.selectByKey(msgKey);
		if(consumerMsg != null ){
			return true;
		}
		return false;
	}

	@Override
	public boolean isProcessing(String msgKey) throws StoreDBCallbackException {
		List<ConsumerMsg> consumerMsgs = consumerMsgMapper.findByMsgKeyAndStatus(msgKey, StoreStatusEnum.CONSUMER_PROCESS.getValue());
		if(consumerMsgs==null || consumerMsgs.size() == 0){
			return false;
		}
		return true;
	}


	@Override
	public void updateMsgSuccess(String msgKey) throws StoreDBCallbackException {
		ConsumerMsg msg = consumerMsgMapper.selectByKey(msgKey);
		if(msg!=null){
			msg.setStatus(StoreStatusEnum.CONSUMER_SUCCESS.getValue());
			msg.setUpdateTime(new Date());
			consumerMsgMapper.updateByKey(msg);
		}else{
			throw new StoreDBCallbackException("can not find msg "+msgKey);
		}
	}

	@Override
	public void updateMsgFaild(String msgKey) throws StoreDBCallbackException {
		ConsumerMsg msg = consumerMsgMapper.selectByKey(msgKey);
		if(msg!=null){
			msg.setStatus(StoreStatusEnum.CONSUMER_FAILD.getValue());
			msg.setUpdateTime(new Date());
			consumerMsgMapper.updateByKey(msg);
		}else{
			throw new StoreDBCallbackException("can not find msg "+msgKey);
		}
		
	}

	@Override
	public void updateMsgProcessing(String msgKey, String data, String exchange, String routerKey,
			String consumerClassName, String bizClassName) throws StoreDBCallbackException {
		ConsumerMsg msg = consumerMsgMapper.selectByKey(msgKey);
		if(msg==null){
			ConsumerMsg msgnew=new ConsumerMsg();
			msgnew.setMsgKey(msgKey);
			msgnew.setCreateTime(new Date());
			msgnew.setStatus(StoreStatusEnum.CONSUMER_PROCESS.getValue());
			msgnew.setUpdateTime(new Date());
			msgnew.setMsgContent(data);
			msgnew.setExchange(exchange);
			msgnew.setRouterKey(routerKey);
			msgnew.setRetryCount(0);
			msgnew.setBizclassName(bizClassName);
			msgnew.setConsumerClassName(consumerClassName);
			consumerMsgMapper.insert(msgnew);
		}else{
			throw new StoreDBCallbackException("can not find msg "+msgKey);
		}
		
	}

	@Override
	public List<ConsumerDto> selectReConsumerList(Integer status) {
		  List<ConsumerDto> producerdtolist=new ArrayList<>();
		  List<ConsumerMsg> list= consumerMsgMapper.findByMsgKeyAndStatus(null, status);
		  for(ConsumerMsg msg:list) {
			 ConsumerDto dto= new ConsumerDto();
			 dto.setMsgKey(msg.getMsgKey());
			 dto.setMsgContent(msg.getMsgContent());
			 dto.setBizClassName(msg.getBizclassName());
			 dto.setMsgContent(msg.getMsgContent());
			 dto.setConsumerClassName(msg.getConsumerClassName());
			 producerdtolist.add(dto);
		  }
		  return producerdtolist;
	}
	

}
