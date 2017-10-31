package com.yonyou.cloud.mom.demo.msg.callback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.cloud.mom.core.store.callback.ProducerStoreDBCallback;
import com.yonyou.cloud.mom.core.store.callback.exception.StoreDBCallbackException;

@Service
@Transactional
public class DemoMsgProducerCallBack implements ProducerStoreDBCallback {

//	@Autowired
//	MsgDao msgDao;

	@Override
	public void saveStatusData(String msgKey, String data, String exchange, String routerKey)
			throws StoreDBCallbackException {

	}

	@Override
	public void update2InitFailed(String msgKey, String infoMsg, Long costTime) throws StoreDBCallbackException {
	}

}
