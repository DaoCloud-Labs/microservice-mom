package com.yonyou.cloud.mom.demo.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.cloud.mom.client.MqSender;
import com.yonyou.cloud.mom.demo.dao.TmBizMapper;
import com.yonyou.cloud.mom.demo.entity.TmBiz;
import com.yonyou.cloud.mom.demo.msg.entity.LoginMsg;

@Service
@Transactional
public class BizService {
	
	
	@Autowired
	private MqSender mqSender;
	
	@Autowired
	private TmBizMapper bizDao;
	
	public String saveLoginUser(String name) throws InterruptedException{
		
		TmBiz tmBiz = new TmBiz();
		tmBiz.setName(name);
		bizDao.insert(tmBiz);
		
		LoginMsg msg = new LoginMsg();
		msg.setLoginName(name);
		msg.setLoginTime(new Date().getTime());
		
		mqSender.send("amqpExchange", "login", msg);
		if(name.equals("interruption")) throw new RuntimeException();
		return "success";
	}
	
}
