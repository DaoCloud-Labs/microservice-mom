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
import com.yonyou.cloud.mom.demo.dao.ConsumerDao;
import com.yonyou.cloud.mom.demo.msg.entity.ConsumerEntity;

@Service
@Transactional
public class DemoMsgConsumerCallBack implements ConsumerStoreDbCallback{

	@Autowired
	ConsumerDao consumerDao;

	@Override
	public boolean exist(String msgKey) throws StoreDBCallbackException {
		ConsumerEntity msg = consumerDao.findOne(msgKey);
		if(msg != null ){
			return true;
		}
		return false;
	}

	@Override
	public boolean isProcessing(String msgKey) throws StoreDBCallbackException {
		ConsumerEntity msg = consumerDao.findByMsgKeyAndStatus(msgKey, StoreStatusEnum.CONSUMER_PROCESS.getValue());
		if(msg==null){
//			consumerDao.findOne(msgKey);
//			ConsumerEntity entity=new ConsumerEntity();
//			entity.setMsgKey(msgKey);
			return false;
		}
		return true;
	}

	@Override
	public void updateMsgProcessing(String msgKey,String data,String exchange,String routerKey,String consumerClassName,String bizClassName) throws StoreDBCallbackException {
		ConsumerEntity msg = consumerDao.findOne(msgKey);
		if(msg==null){
			ConsumerEntity msgnew=new ConsumerEntity();
			msgnew.setMsgKey(msgKey);
			msgnew.setStatus(StoreStatusEnum.CONSUMER_PROCESS.getValue());
			msgnew.setUpdateTime(new Date());
			msgnew.setMsgContent(data);
			msgnew.setExchange(exchange);
			msgnew.setRouterKey(routerKey);
			msgnew.setBizClassName(bizClassName);
			msgnew.setConsumerClassName(consumerClassName);
			msgnew.setRetryCount(0);
			consumerDao.save(msgnew);
		}else{
//			throw new StoreDBCallbackException("can not find msg "+msgKey);
			msg.setRetryCount(msg.getRetryCount()+1);
			consumerDao.save(msg);
		}
		
	}

	@Override
	public void updateMsgSuccess(String msgKey) throws StoreDBCallbackException {
		ConsumerEntity msg = consumerDao.findOne(msgKey);
		if(msg!=null){
			msg.setStatus(StoreStatusEnum.CONSUMER_SUCCESS.getValue());
			msg.setUpdateTime(new Date());
			consumerDao.save(msg);
		}else{
			throw new StoreDBCallbackException("can not find msg "+msgKey);
		}
	}

	@Override
	public void updateMsgFaild(String msgKey) throws StoreDBCallbackException {
		ConsumerEntity msg = consumerDao.findOne(msgKey);
		if(msg!=null){
			msg.setStatus(StoreStatusEnum.CONSUMER_FAILD.getValue());
			msg.setUpdateTime(new Date());
			consumerDao.save(msg);
		}else{
			throw new StoreDBCallbackException("can not find msg "+msgKey);
		}
		
	}
	
	@Override
	 public List<ConsumerDto> selectReConsumerList(Integer status){
		List<ConsumerDto> dtolist=new ArrayList<>();
		List<ConsumerEntity> list=consumerDao.findbystatus(status);
		for(ConsumerEntity consumer:list) {
			ConsumerDto dto=new ConsumerDto();
			dto.setMsgKey(consumer.getMsgKey());
			dto.setMsgContent(consumer.getMsgContent());
			dto.setConsumerClassName(consumer.getConsumerClassName());
			dto.setBizClassName(consumer.getBizClassName());
			dto.setStatus(consumer.getStatus());
			dto.setRouterKey(consumer.getRouterKey());
			dto.setRetryCount(consumer.getRetryCount());
			dtolist.add(dto);
		}
		return dtolist;
	 }
	

}
