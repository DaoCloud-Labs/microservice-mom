
### 生产者用法

1.config

``` 
	<dependency>
		<groupId>com.yonyou.cloud</groupId>
		<artifactId>mom-client</artifactId>
		<version>0.0.2-SNAPSHOT</version>
    	</dependency>
```


```
spring.rabbitmq.host=10.180.4.221
spring.rabbitmq.port=5672
spring.rabbitmq.username=mqadmin
spring.rabbitmq.password=Pass1234
```


```
import org.springframework.amqp.rabbit.core.RabbitOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yonyou.cloud.mom.client.impl.MqSenderDefaultImpl;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;

@Configuration
public class MqConfig {
	
		@Bean
		public MqSenderDefaultImpl mqSenderDefaultImpl(RabbitOperations rabbitOperations) {
			MqSenderDefaultImpl mqSenderDefaultImpl = new MqSenderDefaultImpl();
			mqSenderDefaultImpl.setRabbitOperations(rabbitOperations);
			return mqSenderDefaultImpl;
		}
		
		@Bean
		public MessageConverter messageConverter() {
			JsonMessageConverter jsonMessageConverter = new JsonMessageConverter();
			return jsonMessageConverter;
		}
}

```


2.实现回调

``` 				  
@Component
public class ProducerCallbackImpl implements ProducerStoreDBCallback{
	
	@Autowired
	MsgService msgService;

	@Override
	public void saveMsgData(String msgKey, String data, String exchange, String routerKey, String bizClassName)
			throws StoreDBCallbackException {
		
		ProducerMsg msg = new ProducerMsg();
		msg.setMsgKey(msgKey);
		msg.setMsgContent(data);
		msg.setExchange(exchange);
		msg.setRouterKey(routerKey);
		msg.setBizClassName(bizClassName);
		msg.setStatus(0);
		msgService.insert(msg);;
		
	}

	@Override
	public void update2success(String msgKey) throws StoreDBCallbackException {
		ProducerMsg msg = new ProducerMsg();
		msg.setMsgKey(msgKey);
		msg.setStatus(1);
		msgService.updateSelectiveById(msg);
	}

	@Override
	public void update2faild(String msgKey, String infoMsg, Long costTime, String exchange, String routerKey,
			String data, String bizClassName) throws StoreDBCallbackException {
		ProducerMsg msg = new ProducerMsg();
		msg.setMsgKey(msgKey);
		msg.setStatus(2);
		msgService.updateSelectiveById(msg);		
	}

	@Override
	public List<ProducerDto> selectResendList(Integer status) {
		ProducerMsg msg = new ProducerMsg();
		msg.setStatus(2);
		List<ProducerMsg> msgList = msgService.selectList(msg);
		List<ProducerDto> returnList = new ArrayList<ProducerDto>();
		for(int i = 0 ; i<msgList.size();i++) {
			ProducerDto returnDto = new ProducerDto();
			returnDto.setBizClassName(msgList.get(i).getBizClassName());
			returnDto.setExchange(msgList.get(i).getExchange());
			returnDto.setMsgContent(msgList.get(i).getMsgContent());
			returnDto.setMsgKey(msgList.get(i).getMsgKey());
			returnDto.setRouterKey(msgList.get(i).getRouterKey());
			returnDto.setStatus(status);
			returnList.add(returnDto);
		}
		
		return returnList;
	}
}


``` 


3.使用

```
	public boolean userLogin(String userName) {
		//业务逻辑部分
		TmUser user = new TmUser();
		user.setUserName(userName);
		insert(user);
		
		LoginEvent event = new LoginEvent();
		event.setUserName(userName);
		
		
		//发送消息
		mqSender.send("ben_login", "login", event);
		
		return true;
	}

```


### 消费者用法

1.config

``` 
	<dependency>
		<groupId>com.yonyou.cloud</groupId>
		<artifactId>mom-client</artifactId>
		<version>0.0.2-SNAPSHOT</version>
    	</dependency>
```


```
spring.rabbitmq.host=10.180.4.221
spring.rabbitmq.port=5672
spring.rabbitmq.username=mqadmin
spring.rabbitmq.password=Pass1234
```

```
import org.ben.mom.consumer.listener.LoginEventListener;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitOperations;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yonyou.cloud.mom.client.impl.MqSenderDefaultImpl;

@Configuration
public class MqConfig {
	@Bean
	public Queue pointsListenLoginQueue() {
		return new Queue("consumer-a", true); // 队列持久
	}

	@Bean
	public FanoutExchange eventExchange() {
		return new FanoutExchange("ben_login");
	}

	@Bean
	public Binding PointsBindingLogin() {
		return BindingBuilder.bind(pointsListenLoginQueue()).to(eventExchange());
//				.with("queue-key");
	}

	@Bean
	public SimpleMessageListenerContainer messageContainer1(ConnectionFactory connectionFactory,
			LoginEventListener loginEventListener) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
		container.setQueues(pointsListenLoginQueue());
		container.setExposeListenerChannel(true);
		container.setMaxConcurrentConsumers(1);
		container.setConcurrentConsumers(1);
		container.setAcknowledgeMode(AcknowledgeMode.MANUAL); // 设置确认模式手工确认
		container.setMessageListener(loginEventListener);
		container.setMaxConcurrentConsumers(10);//设置最大消费者数量 防止大批量涌入
		return container;
	}
	
	@Bean
	public MessageConverter messageConverter() {
		JsonMessageConverter jsonMessageConverter = new JsonMessageConverter();
		return jsonMessageConverter;
	}
	
	@Bean
	public MqSenderDefaultImpl mqSenderDefaultImpl(RabbitOperations rabbitOperations) {
		MqSenderDefaultImpl mqSenderDefaultImpl = new MqSenderDefaultImpl();
		mqSenderDefaultImpl.setRabbitOperations(rabbitOperations);
		return mqSenderDefaultImpl;
	}
	
}

```

2.实现回调

```
@Component
public class ConsumerCallbackImpl implements ConsumerStoreDbCallback{

	@Autowired
	ConsumerMsgMapper consumerMsgMapper;
	
	@Override
	public boolean exist(String msgKey) throws StoreDBCallbackException {
		if(consumerMsgMapper.selectByPrimaryKey(msgKey)!=null) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isProcessing(String msgKey) throws StoreDBCallbackException {
		ConsumerMsg msg = new ConsumerMsg();
		msg.setMsgKey(msgKey);
		msg.setStatus(103);
		if(consumerMsgMapper.select(msg)!=null&&consumerMsgMapper.select(msg).size()==1) {
			return true;
		}
	
		return false;
	}

	@Override
	public void updateMsgProcessing(String msgKey, String data, String exchange, String routerKey,
			String consumerClassName, String bizClassName) throws StoreDBCallbackException {
		ConsumerMsg msg = new ConsumerMsg();
		msg.setMsgKey(msgKey);
		
		if(consumerMsgMapper.selectOne(msg)!=null) {
			msg.setStatus(103);
			consumerMsgMapper.updateByPrimaryKeySelective(msg);
		}else {
			msg.setBizClassName(bizClassName);
			msg.setConsumerClassName(consumerClassName);
			msg.setCreateTime(new Date());
			msg.setExchange(exchange);
			msg.setMsgContent(data);
			msg.setMsgKey(msgKey);
			msg.setRouterKey(routerKey);
			msg.setStatus(103);
			consumerMsgMapper.insert(msg);
		}
	}

	@Override
	public void updateMsgSuccess(String msgKey) throws StoreDBCallbackException {
		ConsumerMsg msg = new ConsumerMsg();
		msg.setMsgKey(msgKey);
		msg.setStatus(101);
		consumerMsgMapper.updateByPrimaryKeySelective(msg);
	}

	@Override
	public void updateMsgFaild(String msgKey) throws StoreDBCallbackException {
		ConsumerMsg msg = new ConsumerMsg();
		msg.setMsgKey(msgKey);
		msg.setStatus(102);
		consumerMsgMapper.updateByPrimaryKeySelective(msg);
		
	}

	@Override
	public List<ConsumerDto> selectReConsumerList(Integer status) {
		return null;
	}

}
```

3.实现监听逻辑

```
@Component
public class LoginEventListener extends AbstractConsumerListener<LoginEvent>{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	UserService userService;
	
	@Autowired
	TmUserMapper tmUserMapper;

	@Override
	protected void handleMessage(LoginEvent data) {
		logger.info("处理登录事件");
		String userName = data.getUserName();
		Example example = new Example(TmUser.class);
		example.createCriteria().andEqualTo("userName", userName);
		TmUser user = new TmUser();
		user.setUserName(userName+"a");
		tmUserMapper.updateByExampleSelective(user, example);
	}
	
}

```
