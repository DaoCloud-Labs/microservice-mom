package com.yonyou.cloud.mom.demo.msg.callback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.cloud.mom.core.dto.ProducerDto;
import com.yonyou.cloud.mom.core.store.StoreStatusEnum;
import com.yonyou.cloud.mom.core.store.callback.ProducerStoreDBCallback;
import com.yonyou.cloud.mom.core.store.callback.exception.StoreDBCallbackException;
import com.yonyou.cloud.mom.demo.dao.ProducerMsgMapper;
import com.yonyou.cloud.mom.demo.entity.ProducerMsg;

@Service
@Transactional
public class DemoMsgProducerCallBack implements ProducerStoreDBCallback {
	Logger log=LoggerFactory.getLogger(DemoMsgProducerCallBack.class);
	
	@Autowired
	ProducerMsgMapper msgMapper;

	@Override
	public void saveMsgData(String msgKey, String data, String exchange, String routerKey, String bizClassName)
			throws StoreDBCallbackException {
		System.out.println("进入存储消息的逻辑" + msgKey);
		ProducerMsg producerMsg = new ProducerMsg();
		producerMsg.setMsgKey(msgKey);
		producerMsg.setMsgContent(data);
		producerMsg.setExchange(exchange);
		producerMsg.setRouterKey(routerKey);
		producerMsg.setStatus(StoreStatusEnum.PRODUCER_INIT.getValue());
		producerMsg.setRetryCount(0);
		producerMsg.setCreateTime(new Date());
		producerMsg.setBizclassName(bizClassName);
		msgMapper.insert(producerMsg);
		
	}

	@Override
	public void update2success(String msgKey) throws StoreDBCallbackException {
		log.info("消息发送成功" + msgKey);
		ProducerMsg msg=msgMapper.selectByKey(msgKey);
		msg.setStatus(StoreStatusEnum.PRODUCER_SUCCESS.getValue());
		msg.setUpdateTime(new Date());
		msgMapper.updateByKey(msg);
	}

	@Override
	public void update2faild(String msgKey, String infoMsg, Long costTime, String exchange, String routerKey,
			String data, String bizClassName) throws StoreDBCallbackException {
		ProducerMsg msg = new ProducerMsg();
		msg.setStatus(StoreStatusEnum.PRODUCER_FAILD.getValue());
		msg.setInfoMsg(infoMsg);
		msg.setUpdateTime(new Date());
		msg.setMsgKey(msgKey);
		msg.setBizclassName(bizClassName);
		msg.setExchange(exchange);
		msg.setRouterKey(routerKey);
		msg.setMsgContent(data);
		msgMapper.updateByKey(msg);
	}

	@Override
	public List<ProducerDto> selectResendList(Integer status) {

		  log.info("扫描需要重新发送的消息" + status);
		  
		  List<ProducerDto> producerdtolist=new ArrayList<>();
		  List<ProducerMsg> list= msgMapper.selectByStatus(status);
		  for(ProducerMsg msg:list) {
			 ProducerDto dto= new ProducerDto();
			 dto.setExchange(msg.getExchange());
			 dto.setMsgKey(msg.getMsgKey());
			 dto.setRouterKey(msg.getRouterKey());
			 dto.setMsgContent(msg.getMsgContent());
			 dto.setBizClassName(msg.getBizclassName());
			 dto.setMsgContent(msg.getMsgContent());
			 producerdtolist.add(dto);
		  }
		  return producerdtolist;
	}

}
