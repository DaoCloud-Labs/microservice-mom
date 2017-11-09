package com.yonyou.cloud.mom.demo.msg.listener;

import org.springframework.stereotype.Service;

import com.yonyou.cloud.mom.client.consumer.AbstractConsumerListener;
import com.yonyou.cloud.mom.demo.msg.entity.LoginMsg;

@Service
public class PointsListenLogin extends AbstractConsumerListener<LoginMsg>{
	
	private static int counter = 0;

	@Override
	public void handleMessage(LoginMsg data) {
		LOGGER.debug("监听到有人登录了，用户名："+data.getLoginName()+"，发送积分");
		LOGGER.debug("count:{}",++counter);
	}

}
