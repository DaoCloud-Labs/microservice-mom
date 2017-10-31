package com.yonyou.cloud.mom.demo.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.yonyou.cloud.mom.demo.service.BizService;

@Configuration
@EnableScheduling
@Component
public class resendtask {
	private static Logger log = LoggerFactory.getLogger(resendtask.class);
	
	@Autowired
	private BizService bizsrv;
	
	@Scheduled(fixedRate = 60000)
	public void exposeTokenTime() {
//		try {
//			bizsrv.saveLoginUser("登录信息");
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}
}
