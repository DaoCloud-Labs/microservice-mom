package com.yonyou.cloud.mom.demo.msg.callback;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.cloud.mom.core.dto.ProducerDto;
import com.yonyou.cloud.mom.core.store.callback.ProducerStoreDBCallback;
import com.yonyou.cloud.mom.core.store.callback.exception.StoreDBCallbackException;

@Service
@Transactional
public class DemoMsgProducerCallBack implements ProducerStoreDBCallback {

	@Override
	public void saveMsgData(String msgKey, String data, String exchange, String routerKey, String bizClassName)
			throws StoreDBCallbackException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update2success(String msgKey) throws StoreDBCallbackException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update2faild(String msgKey, String infoMsg, Long costTime) throws StoreDBCallbackException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ProducerDto> selectResendList(Integer status) {
		// TODO Auto-generated method stub
		return null;
	}


}
