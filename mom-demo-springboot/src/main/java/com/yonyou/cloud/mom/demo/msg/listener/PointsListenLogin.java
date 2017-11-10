package com.yonyou.cloud.mom.demo.msg.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.cloud.mom.client.MqSender;
import com.yonyou.cloud.mom.client.consumer.AbstractConsumerListener;
import com.yonyou.cloud.mom.demo.dao.BizDao;
import com.yonyou.cloud.mom.demo.dao.ConsumerDao;
import com.yonyou.cloud.mom.demo.msg.entity.LoginMsg;

@Service
public class PointsListenLogin extends AbstractConsumerListener<LoginMsg>{
	
	@Autowired
	public ConsumerDao consumerDao;
	
	@Override
	protected void handleMessage(LoginMsg data) {
		long fff=consumerDao.count();
		LOGGER.debug("监听到有人登录了，用户名："+data.getLoginName()+"，发送积分"+fff);
	}

}
