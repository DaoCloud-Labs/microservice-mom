package com.yonyou.cloud.mom.client.impl.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yonyou.cloud.mom.client.impl.MqSenderDefaultImpl;
import com.yonyou.cloud.mom.demo.MomDemoSpringbootApplication;

/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class) //指定测试用例的运行器 这里是指定了Junit4    
@SpringBootTest(classes=MomDemoSpringbootApplication.class)    
public class AppTest 
{
	@Autowired
	MqSenderDefaultImpl  mqSenderDefaultImpl;
	
	
	@Test
	public void testSender() {
		mqSenderDefaultImpl.send("a", "ee", "123");
		mqSenderDefaultImpl.send("a", "ee", "123");
		mqSenderDefaultImpl.send("a", "ee", "123");
		mqSenderDefaultImpl.send("a", "ee", "123");
		mqSenderDefaultImpl.send("a", "ee", "123");
		mqSenderDefaultImpl.send("a", "ee", "123");
		mqSenderDefaultImpl.send("a", "ee", "123");
		mqSenderDefaultImpl.send("a", "ee", "123");
		mqSenderDefaultImpl.send("a", "ee", "123");
		mqSenderDefaultImpl.send("a", "ee", "123");
		mqSenderDefaultImpl.send("a", "ee", "123");
		mqSenderDefaultImpl.send("a", "ee", "123");
		mqSenderDefaultImpl.send("a", "ee", "123");
		mqSenderDefaultImpl.send("a", "ee", "123");
		mqSenderDefaultImpl.send("a", "ee", "123");
		mqSenderDefaultImpl.send("a", "ee", "123");
		mqSenderDefaultImpl.send("a", "ee", "123");
		mqSenderDefaultImpl.send("a", "ee", "123");
		mqSenderDefaultImpl.send("a", "ee", "123");
		mqSenderDefaultImpl.send("a", "ee", "123");
		mqSenderDefaultImpl.send("a", "ee", "123");
		mqSenderDefaultImpl.send("a", "ee", "123");
		mqSenderDefaultImpl.send("a", "ee", "123");
		mqSenderDefaultImpl.send("a", "ee", "123");
		mqSenderDefaultImpl.send("a", "ee", "123");
		mqSenderDefaultImpl.send("a", "ee", "123");
		mqSenderDefaultImpl.send("a", "ee", "123");
		mqSenderDefaultImpl.send("a", "ee", "123");
	}
	
}
