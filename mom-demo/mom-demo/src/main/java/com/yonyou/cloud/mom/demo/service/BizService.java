package com.yonyou.cloud.mom.demo.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.cloud.mom.client.MqSender;
import com.yonyou.cloud.mom.demo.msg.entity.BizEntity;
import com.yonyou.cloud.mom.demo.msg.entity.LoginMsg;

@Service
@Transactional
public class BizService {
	
	
	@Autowired
	private MqSender mqSender;
	
//	@Autowired
//	private BizDao bizDao;
	
	public String saveLoginUser(String name) throws InterruptedException{
		
		BizEntity e = new BizEntity();
		e.setId(name);
		e.setName(name);
//		bizDao.save(e);
		
		LoginMsg msg = new LoginMsg();
		msg.setLoginName(name);
		msg.setLoginTime(new Date().getTime());
		
		mqSender.send("event-exchange", "login", msg);
		
		return "1";
	}

}
