package com.yonyou.cloud.mom.demo.msg.callback;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.cloud.mom.core.store.StoreStatusEnum;
import com.yonyou.cloud.mom.core.store.callback.ConsumerStoreDbCallback;
import com.yonyou.cloud.mom.core.store.callback.exception.StoreDBCallbackException;
import com.yonyou.cloud.mom.demo.msg.entity.MsgEntity;

@Service
@Transactional
public class DemoMsgConsumerCallBack implements ConsumerStoreDbCallback{

//	@Autowired
//	MsgDao msgDao;

	@Override
	public boolean exist(String msgKey) throws StoreDBCallbackException {
//		MsgEntity msg = msgDao.findOne(msgKey);
//		if(msg != null ){
//			return true;
//		}
		return false;
	}

	@Override
	public boolean isProcessing(String msgKey) throws StoreDBCallbackException {
		return false;
//		MsgEntity msg = msgDao.findByMsgKeyAndStatus(msgKey, StoreStatusEnum.CONSUMER_PROCESS.getValue());
//		if(msg==null){
//			return false;
//		}
//		return true;
	}

	@Override
	public void updateMsgProcessing(String msgKey) throws StoreDBCallbackException {
//		MsgEntity msg = msgDao.findOne(msgKey);
//		if(msg!=null){
//			msg.setStatus(StoreStatusEnum.CONSUMER_PROCESS.getValue());
//			msg.setUpdateTime(new Date().getTime());
//			msgDao.save(msg);
//		}else{
//			throw new StoreDBCallbackException("can not find msg "+msgKey);
//		}
		
	}

	@Override
	public void updateMsgSuccess(String msgKey) throws StoreDBCallbackException {
//		MsgEntity msg = msgDao.findOne(msgKey);
//		if(msg!=null){
//			msg.setStatus(StoreStatusEnum.CONSUMER_SUCCESS.getValue());
//			msg.setUpdateTime(new Date().getTime());
//			msgDao.save(msg);
//		}else{
//			throw new StoreDBCallbackException("can not find msg "+msgKey);
//		}
	}

	@Override
	public void updateMsgFaild(String msgKey) throws StoreDBCallbackException {
//		MsgEntity msg = msgDao.findOne(msgKey);
//		if(msg!=null){
//			msg.setStatus(StoreStatusEnum.CONSUMER_FAILD.getValue());
//			msg.setUpdateTime(new Date().getTime());
//			msgDao.save(msg);
//		}else{
//			throw new StoreDBCallbackException("can not find msg "+msgKey);
//		}
		
	}
	

}
