package com.yonyou.cloud.mom.demo.msg.callback;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.cloud.mom.core.store.StoreStatusEnum;
import com.yonyou.cloud.mom.core.store.callback.ProducerStoreDBCallback;
import com.yonyou.cloud.mom.core.store.callback.exception.StoreDBCallbackException;
import com.yonyou.cloud.mom.demo.dao.MsgDao;
import com.yonyou.cloud.mom.demo.msg.entity.MsgEntity;

@Service
@Transactional
public class DemoMsgProducerCallBack implements ProducerStoreDBCallback {

	@Autowired
	MsgDao msgDao;

	@Override
	public void saveStatusData(String msgKey, String data, String exchange, String routerKey)
			throws StoreDBCallbackException {
		System.out.println("进入存储消息的逻辑" + msgKey);
		MsgEntity msg = new MsgEntity();
		msg.setMsgKey(msgKey);
		msg.setExchange(exchange);
		msg.setRouterKey(routerKey);
		msg.setStatus(StoreStatusEnum.PRODUCER_INIT.getValue());
		msg.setRetryCount(0);
		msg.setCreateTime(new Date().getTime());
		msgDao.save(msg);

	}

	@Override
	public void update2InitFailed(String msgKey, String infoMsg, Long costTime) throws StoreDBCallbackException {
		System.out.println("进入消息发送失败的逻辑" + msgKey);
		MsgEntity msg = new MsgEntity();
		msg.setMsgKey(msgKey);
		msg.setStatus(StoreStatusEnum.PRODUCER_FAILD.getValue());
		msg.setInfoMsg(infoMsg);
		msg.setUpdateTime(new Date().getTime());
		msgDao.save(msg);
	}

}
