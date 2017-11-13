pom文件

配置pom包，主要是进入消息中间件mom-client 和对spring-boot-starter-amqp的支持
		因为这里用到JPA 所有加入JPA相关支持


``` 
	<dependency>
			<groupId>com.yonyou.cloud</groupId>
			<artifactId>mom-client</artifactId>
			<version>1.0-SNAPSHOT</version>
    </dependency>
```


``` 
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<groupId>com.yonyou.cloud</groupId>
	<artifactId>mom-demo-springboot</artifactId>
	<version>1.0-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>mom-demo-springboot</name>
	<description>Demo project for Spring Boot</description>

	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>1.4.7.RELEASE</version>
		<relativePath /> <!-- lookup parent from repository -->
	</parent>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
		<java.version>1.8</java.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>com.yonyou.cloud</groupId>
			<artifactId>mom-client</artifactId>
			<version>1.0-SNAPSHOT</version>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-amqp</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-aop</artifactId>
		</dependency>  
		 
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>


</project>
``` 




配置文件

因为本demo持久层用的spring data JPA 所以有关于JPA相关配置
application.properties

``` 
spring.application.name=mom-demo-springboot
server.port=8181
spring.rabbitmq.host=10.180.8.171
spring.rabbitmq.port=5672
spring.rabbitmq.username=test
spring.rabbitmq.password=test
 
#datasource
spring.datasource.driverClassName=com.mysql.jdbc.Driver
spring.datasource.url=jdbc\:mysql\://127.0.0.1\:3306/mq_data?autoReconnect\=true&useUnicode\=true&characterEncoding\=UTF-8
spring.datasource.username=root
spring.datasource.password=root

#jpa
spring.jpa.database-platform=org.hibernate.dialect.MySQL5Dialect
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.openInView=true
spring.jpa.properties.hibernate.show_sql=true
logging.level.com.yonyou=debug
logging.level.org.springframework.orm.jpa=debug


#Re-resend the task  
jobs.resend.schedule=0/30 * * * * *
# Re-consume the task
jobs.reconsumer.schedule=0/30 * * * * *

``` 


消息中间件初始化配置

对发消息者、队列、收消息者 相关配置

交换机(Exchange)
交换机有四种类型：Direct, topic, Headers and Fanout 可根据具体场景需要配置;这里我们选着direct "先匹配, 再投送"即在绑定时设定一个 routing_key, 消息的routing_key 匹配时, 才会被交换器投送到绑定的队列中去.
 
创建连接 
一般使用RabbitTemplate 和AmqpTemplate创建链接，
他们的的关系如下：RabbitTemplate   implements  RabbitOperations ；
				  RabbitOperations  extends     AmqpTemplate
我们这里使用RabbitTemplate 创建连接

初始化生产者相关实现类
``` 				  
package com.yonyou.cloud.mom.demo.config;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitOperations;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.yonyou.cloud.mom.client.impl.MqSenderDefaultImpl;
import com.yonyou.cloud.mom.core.store.ProducerMsgStore;
import com.yonyou.cloud.mom.core.store.impl.DbStoreProducerMsg;
import com.yonyou.cloud.mom.core.util.SpringUtil;
import com.yonyou.cloud.mom.demo.msg.listener.PointsListenLogin;

@Configuration
@ComponentScan(basePackages = "com.yonyou.cloud.mom")
public class MqConfig {
	
	
	//初始化生产者相关实现类
	@Bean
	public MqSenderDefaultImpl mqSenderDefaultImpl(RabbitOperations rabbitOperations) {
		MqSenderDefaultImpl mqSenderDefaultImpl = new MqSenderDefaultImpl();
		mqSenderDefaultImpl.setRabbitOperations(rabbitOperations);
		return mqSenderDefaultImpl;
	}

	@Bean
	public SpringUtil springUtil() {
		return new SpringUtil();
	}
 
	@Bean
	public Queue pointsListenLoginQueue() {
		return new Queue("points-login", true); // 队列持久
	}

	//设定交换机类型
	@Bean
	public DirectExchange eventExchange() {
		return new DirectExchange("event-exchange");
	}

	//交换机和队列绑定
	@Bean
	public Binding PointsBindingLogin() {
		return BindingBuilder.bind(pointsListenLoginQueue()).to(eventExchange()).with("login");
	}

	
	//初始化监听者
	@Bean
	public SimpleMessageListenerContainer messageContainer1(ConnectionFactory connectionFactory,
			PointsListenLogin pointsListenLogin) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
		container.setQueues(pointsListenLoginQueue());
		container.setExposeListenerChannel(true);
		container.setMaxConcurrentConsumers(1);
		container.setConcurrentConsumers(1);
		container.setAcknowledgeMode(AcknowledgeMode.MANUAL); // 设置确认模式手工确认
		container.setMessageListener(pointsListenLogin);
		container.setMaxConcurrentConsumers(10);//设置最大消费者数量 防止大批量涌入
		return container;
	}

	@Bean
	public MessageConverter messageConverter() {
		JsonMessageConverter jsonMessageConverter = new JsonMessageConverter();
		return jsonMessageConverter;
	}
 
}

``` 

实现生产者相关接口
``` 
package com.yonyou.cloud.mom.demo.msg.callback;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.cloud.mom.client.MqSender;
import com.yonyou.cloud.mom.core.dto.ProducerDto;
import com.yonyou.cloud.mom.core.store.StoreStatusEnum;
import com.yonyou.cloud.mom.core.store.callback.ProducerStoreDBCallback;
import com.yonyou.cloud.mom.core.store.callback.exception.StoreDBCallbackException;
import com.yonyou.cloud.mom.demo.dao.MsgDao;
import com.yonyou.cloud.mom.demo.msg.entity.MsgEntity;

@Service
@Transactional
public class DemoMsgProducerCallBack implements ProducerStoreDBCallback {
	Logger log=LoggerFactory.getLogger(DemoMsgProducerCallBack.class);
	
	@Autowired
	MsgDao msgDao;
	
	@Autowired
	private MqSender mqSender;
	

	@Override
	public void saveMsgData(String msgKey, String data, String exchange, String routerKey,String bizClassName)
			throws StoreDBCallbackException {
		System.out.println("进入存储消息的逻辑" + msgKey);
			MsgEntity msg = new MsgEntity();
			msg.setMsgKey(msgKey);
			msg.setExchange(exchange);
			msg.setRouterKey(routerKey);
			msg.setStatus(StoreStatusEnum.PRODUCER_INIT.getValue());
			msg.setRetryCount(0);
			msg.setCreateTime(new Date().getTime());
			msg.setBizClassName(bizClassName);
			msg.setMsgContent(data);
			msgDao.save(msg);
	}
	
	
	@Override
	public void update2success(String msgKey) throws StoreDBCallbackException {
		
			log.info("消息发送成功" + msgKey);
			MsgEntity msg=msgDao.findOne(msgKey);
			msg.setStatus(StoreStatusEnum.PRODUCER_SUCCESS.getValue());
			msgDao.save(msg);
	}

	@Override
	public void update2faild(String msgKey, String infoMsg, Long costTime,String exchange, String routerKey,String data,String bizClassName) throws StoreDBCallbackException {
		log.info("进入消息发送失败的逻辑" + msgKey);
		MsgEntity msg = new MsgEntity();
		msg.setMsgKey(msgKey);
		msg.setStatus(StoreStatusEnum.PRODUCER_FAILD.getValue());
		msg.setInfoMsg(infoMsg);
		msg.setUpdateTime(new Date().getTime());
		msg.setBizClassName(bizClassName);
		msg.setExchange(exchange);
		msg.setRouterKey(routerKey);
		msg.setMsgContent(data);
		msgDao.save(msg);
	}
	
  
	
	  @Override
	  public List<ProducerDto> selectResendList(Integer status){
		  
		  log.info("扫描需要重新发送的消息" + status);
		  
		  List<ProducerDto> producerdtolist=new ArrayList<>();
		  List<MsgEntity> list= msgDao.findbystatus(status);
		  for(MsgEntity msg:list) {
			 ProducerDto dto= new ProducerDto();
			 dto.setExchange(msg.getExchange());
			 dto.setMsgKey(msg.getMsgKey());
			 dto.setRouterKey(msg.getRouterKey());
			 dto.setMsgContent(msg.getMsgContent());
			 dto.setBizClassName(msg.getBizClassName());
			 dto.setMsgContent(msg.getMsgContent());
			  producerdtolist.add(dto);
		  }
		  return producerdtolist;
	  }

}
``` 

消息发送
		
		注入发送mq的默认实现
		@Autowired
		private MqSender mqSender;
		
		通过send方法发送 （第一个参数是交换机名称；第二个参数是队列名称；第三个参数是发送的对象）
		
		LoginMsg msg = new LoginMsg();
		msg.setLoginName(name);
		msg.setLoginTime(new Date().getTime());
		mqSender.send("event-exchange", "login", msg);


消费者相关
消费者需要继承中间件 AbstractConsumerListener类 实现handleMessage接口（拿到消息，实现相关消息消费逻辑）
``` 
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

``` 


实现消费者相关接口
``` 
package com.yonyou.cloud.mom.demo.msg.callback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.cloud.mom.core.dto.ConsumerDto;
import com.yonyou.cloud.mom.core.store.StoreStatusEnum;
import com.yonyou.cloud.mom.core.store.callback.ConsumerStoreDbCallback;
import com.yonyou.cloud.mom.core.store.callback.exception.StoreDBCallbackException;
import com.yonyou.cloud.mom.demo.dao.ConsumerDao;
import com.yonyou.cloud.mom.demo.msg.entity.ConsumerEntity;
import com.yonyou.cloud.mom.demo.msg.entity.MsgEntity;

@Service
@Transactional
public class DemoMsgConsumerCallBack implements ConsumerStoreDbCallback{

	@Autowired
	ConsumerDao consumerDao;

	@Override
	public boolean exist(String msgKey) throws StoreDBCallbackException {
		ConsumerEntity msg = consumerDao.findOne(msgKey);
		if(msg != null ){
			return true;
		}
		return false;
	}

	@Override
	public boolean isProcessing(String msgKey) throws StoreDBCallbackException {
		log.info("判断是否已经接受过这条消息" + msgKey);
		MsgEntity msg = consumerDao.findByMsgKeyAndStatus(msgKey, StoreStatusEnum.CONSUMER_PROCESS.getValue());
		if(msg==null){
//			consumerDao.findOne(msgKey);
//			ConsumerEntity entity=new ConsumerEntity();
//			entity.setMsgKey(msgKey);
			return false;
		}
		return true;
	}

	@Override
	public void updateMsgProcessing(String msgKey,String data,String exchange,String routerKey,String consumerClassName,String bizClassName) throws StoreDBCallbackException {
		log.info("保存接受到的消息" + msgKey);
		ConsumerEntity msg = consumerDao.findOne(msgKey);
		if(msg==null){
			ConsumerEntity msgnew=new ConsumerEntity();
			msgnew.setMsgKey(msgKey);
			msgnew.setStatus(StoreStatusEnum.CONSUMER_PROCESS.getValue());
			msgnew.setUpdateTime(new Date().getTime());
			msgnew.setMsgContent(data);
			msgnew.setExchange(exchange);
			msgnew.setRouterKey(routerKey);
			msgnew.setBizClassName(bizClassName);
			msgnew.setConsumerClassName(consumerClassName);
			consumerDao.save(msgnew);
		}else{
			throw new StoreDBCallbackException("can not find msg "+msgKey);
		}
		
	}

	@Override
	public void updateMsgSuccess(String msgKey) throws StoreDBCallbackException {
	
		log.info("消费成功" + msgKey)
		ConsumerEntity msg = consumerDao.findOne(msgKey);
		if(msg!=null){
			msg.setStatus(StoreStatusEnum.CONSUMER_SUCCESS.getValue());
			msg.setUpdateTime(new Date().getTime());
			consumerDao.save(msg);
		}else{
			throw new StoreDBCallbackException("can not find msg "+msgKey);
		}
	}

	@Override
	public void updateMsgFaild(String msgKey) throws StoreDBCallbackException {
	
		log.info("消费失败" + msgKey)
		ConsumerEntity msg = consumerDao.findOne(msgKey);
		if(msg!=null){
			msg.setStatus(StoreStatusEnum.CONSUMER_FAILD.getValue());
			msg.setUpdateTime(new Date().getTime());
			consumerDao.save(msg);
		}else{
			throw new StoreDBCallbackException("can not find msg "+msgKey);
		}
		
	}
	
	@Override
	 public List<ConsumerDto> selectReConsumerList(Integer status){
		log.info("扫描哪些需要重新消费的消息" + msgKey)
		List<ConsumerDto> dtolist=new ArrayList<>();
		List<ConsumerEntity> list=consumerDao.findbystatus(status);
		for(ConsumerEntity consumer:list) {
			ConsumerDto dto=new ConsumerDto();
			dto.setMsgKey(consumer.getMsgKey());
			dto.setMsgContent(consumer.getMsgContent());
			dto.setConsumerClassName(consumer.getConsumerClassName());
			dto.setBizClassName(consumer.getBizClassName());
			dto.setStatus(consumer.getStatus());
//			dto.setRetryCount(consumer.getRetryCount());
			dtolist.add(dto);
		}
		return dtolist;
	 }
	

}
``` 


生产者数据操作接口ProducerStoreDBCallback方法以及参数说明
 
 
    //保存消息（消息key,消息内容，交换机名称，队列名称，消息对象类名）
    void saveMsgData(String msgKey, String data, String exchange, String routerKey,String bizClassName) throws StoreDBCallbackException;   
   
   //发送成功 （消息key）
    void update2success(String msgKey)throws StoreDBCallbackException;

    //处理为初始化失败 （消息key,消息内容，发送时长，交换机名称，队列名称，消息内容，消息对象类名）
    void update2faild(String msgKey, String infoMsg, Long costTime,String exchange, String routerKey,String data,String bizClassName) throws StoreDBCallbackException;
    
    //获取需要重新发送的内容（消息状态）
    public List<ProducerDto> selectResendList(Integer status);
 
 
 
消费者数据操作接口ConsumerStoreDbCallback方法及参数说明  

    
	//根据msgkey判断消息是否已经存在（消息key） 
    boolean exist(String msgKey) throws StoreDBCallbackException;

    //根据msgkey判断消息是否在处理中 （消息key）
    boolean isProcessing(String msgKey) throws StoreDBCallbackException;
    
    
    //更新消息状态为处理中 （消息key，消息内容，交换机名称，队列名称，消费者对象名称，消息体对象名称）
    void updateMsgProcessing(String msgKey,String data,String exchange,String routerKey,String consumerClassName,String bizClassName) throws StoreDBCallbackException;
    
    
    // 更新为成功 （消息key）
    void updateMsgSuccess(String msgKey) throws StoreDBCallbackException;
    
    
    //更新为失败 （消息key）
    void updateMsgFaild(String msgKey) throws StoreDBCallbackException;
    
    //获取需要重新消费的内容 （消息状态）
    public List<ConsumerDto> selectReConsumerList(Integer status);
 

消息中间件消息状态StoreStatusEnum枚举
 
 	PRODUCER_INIT(0),      消息发送初始化
	PRODUCER_SUCCESS(1),   消息发送成功
	PRODUCER_FAILD(2),     消息发送失败
	CONSUMER_PROCESS(100), 消息消费初始化
	CONSUMER_SUCCESS(101), 消息消费成功
	CONSUMER_FAILD(102);   消息消费失败
	
ProducerDto
    private String msgKey;
	private String msgContent;
	private Integer status;
	private String exchange;
	private String routerKey;
	private String bizClassName;
	
消费者 ConsumerDto  
	private String msgKey;
	private String msgContent;
	private Integer status;
	private String infoMsg;
	private Integer retryCount;
	private String consumerClassName;//消费者类名
	private String bizClassName;






