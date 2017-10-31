package com.yonyou.cloud.mom.client.task;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.yonyou.cloud.mom.client.MqSender;

@Configuration
@EnableScheduling
@Component
public class ResendScheduled {
	private static Logger log = LoggerFactory.getLogger(ResendScheduled.class);
	
	@Autowired
	private  MqSender  MqSender;

	
	//@Scheduled(fixedRate = 6000)
	//@Scheduled(cron = "0/30 * * * * ?") 
	@Scheduled(cron ="${jobs.resend.schedule}")	
	public void exposeTokenTime() {
		log.info("重新发送"+new Date());
		MqSender.resend();
	}
}
