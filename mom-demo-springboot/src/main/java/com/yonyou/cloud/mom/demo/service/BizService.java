package com.yonyou.cloud.mom.demo.service;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.cloud.mom.client.MqSender;
import com.yonyou.cloud.mom.demo.dao.BizDao;
import com.yonyou.cloud.mom.demo.msg.entity.BizEntity;
import com.yonyou.cloud.mom.demo.msg.entity.LoginMsg;
 

@Service
@Transactional
public class BizService {
	
	
	@Autowired
	 MqSender mqSender;
	
	@Autowired
	 BizDao bizDao;
	
	
	public String saveLoginUser(String name) throws InterruptedException{
		
		BizEntity e = new BizEntity();
		e.setId(new Date().toString());
		e.setName(name+System.currentTimeMillis());
		bizDao.save(e);
		
		LoginMsg msg = new LoginMsg();
		msg.setLoginName(name);
		msg.setLoginTime(new Date().getTime());
		
		mqSender.send("exchange-name", "queue-key", msg);
		
		return "1";
	}

}
