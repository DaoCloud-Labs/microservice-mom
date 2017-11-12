# 在Springmvc的项目中使用MOM组件
## 生产者使用说明
### 如何配置

---

添加依赖

```
<dependency>
	<groupId>com.yonyou.cloud</groupId>
	<artifactId>mom-client</artifactId>
	<version>{client.version}</version>
</dependency>
```

---

配置文件中，首先需要增加命名空间。

```
xmlns:rabbit="http://www.springframework.org/schema/rabbit"

```

---

模式文档
    
```
xsi:schemaLocation="
     http://www.springframework.org/schema/rabbit
     http://www.springframework.org/schema/rabbit/spring-rabbit-1.6.xsd"
```

---

配置connection-factory
```
<!-- 连接配置 -->
<rabbit:connection-factory id="connectionFactory" 
            	    host="${spring.rabbitmq.host}" 
		    username="${spring.rabbitmq.username}" 
		    password="${spring.rabbitmq.password}" 
		    port="${spring.rabbitmq.port}"
		    connection-timeout="${spring.rabbitmq.connection-timeout}"
		    channel-cache-size="${spring.rabbitmq.cache.channel.size}"/>
<rabbit:admin connection-factory="connectionFactory"/>
```
参数介绍:

id:bean的id值。

host:RabbitMQ服务器地址。默认值"localhost"。

port:RabbitMQ服务端口，默认值"5672"。

virtual-host:虚拟主机，默认是"/"。

username和password就是访问RabbitMQ服务的账户和密码了。

connection-timeout:链接RabbitMQ服务超时时间。

channel-cache-size:channel的缓存数量。新版本默认是25。

---

配置消息队列queue
```
<!-- 定义queue -->
<rabbit:queue id="pointsListenLoginQueue" name="points-login" durable="true" auto-delete="false" exclusive="false" />
<rabbit:queue id="userLogoutQueue" name="user-Logout" durable="true" auto-delete="false" exclusive="false" />
```
参数介绍:

name:queue的名字。

durable:是否为持久的。默认是true，RabbitMQ重启后queue依然存在。

auto-delete:表示消息队列没有在使用时将被自动删除。默认是false。

exclusive:表示该消息队列是否只在当前connection生效。默认false。

---
配置操作模板
```
<!-- spring template声明-->
<rabbit:template exchange="amqpExchange" id="rabbitTemplate" connection-factory="connectionFactory"  message-converter="messageConverter" />

<!-- 消息对象json转换类 -->
<bean id = "messageConverter" class = "org.springframework.amqp.support.converter.JsonMessageConverter"></bean>
```

---

配置exchange

```
<!-- 定义exchange -->
<rabbit:direct-exchange name="amqpExchange" durable="true" auto-delete="false" id="amqpExchange">
    <rabbit:bindings>
        <rabbit:binding queue="pointsListenLoginQueue" key="login"/>
        <rabbit:binding queue="userLogoutQueue" key="logout"/>
    </rabbit:bindings>
</rabbit:direct-exchange>
```
参数介绍:

name:exchange的名字。

durable:是否为持久的，默认为true，RabbitMQ重启后exhange依然存在。

auto-delete:表示exchange在未被使用时是否自动删除，默认是false。

key:queue在该direct-exchange中的key值。当消息发送给该direct-exchange中指定key为设置值时，消息将会转发给queue参数指定的消息队列。

---

配置mqSender
```
<bean id = "mqSenderDefaultImpl" class = "com.yonyou.cloud.mom.client.impl.MqSenderDefaultImpl">
	<property name="rabbitTemplate" ref="rabbitTemplate"/>
</bean>

```	

---

#### 如何使用

producer需实存储DB的回调接口

```
@Service
@Transactional
public class DemoMsgProducerCallBack implements ProducerStoreDBCallback {
	Logger log=LoggerFactory.getLogger(DemoMsgProducerCallBack.class);
	
	@Autowired
	ProducerMsgMapper msgMapper;

	@Override
	public void saveMsgData(String msgKey, String data, String exchange, String routerKey, String bizClassName)
			throws StoreDBCallbackException {
		System.out.println("进入存储消息的逻辑" + msgKey);
		ProducerMsg producerMsg = new ProducerMsg();
		producerMsg.setMsgKey(msgKey);
		producerMsg.setMsgContent(data);
		producerMsg.setExchange(exchange);
		producerMsg.setRouterKey(routerKey);
		producerMsg.setStatus(StoreStatusEnum.PRODUCER_INIT.getValue());
		producerMsg.setRetryCount(0);
		producerMsg.setCreateTime(new Date());
		producerMsg.setBizclassName(bizClassName);
		msgMapper.insert(producerMsg);
		
	}

	@Override
	public void update2success(String msgKey) throws StoreDBCallbackException {
		log.info("消息发送成功" + msgKey);
		ProducerMsg msg=msgMapper.selectByKey(msgKey);
		msg.setStatus(StoreStatusEnum.PRODUCER_SUCCESS.getValue());
		msg.setUpdateTime(new Date());
		msgMapper.updateByKey(msg);
	}

	@Override
	public void update2faild(String msgKey, String infoMsg, Long costTime, String exchange, String routerKey,
			String data, String bizClassName) throws StoreDBCallbackException {
		ProducerMsg msg = new ProducerMsg();
		msg.setStatus(StoreStatusEnum.PRODUCER_FAILD.getValue());
		msg.setInfoMsg(infoMsg);
		msg.setUpdateTime(new Date());
		msg.setMsgKey(msgKey);
		msg.setBizclassName(bizClassName);
		msg.setExchange(exchange);
		msg.setRouterKey(routerKey);
		msg.setMsgContent(data);
		msgMapper.updateByKey(msg);
	}

	@Override
	public List<ProducerDto> selectResendList(Integer status) {

		  log.info("扫描需要重新发送的消息" + status);
		  
		  List<ProducerDto> producerdtolist=new ArrayList<>();
		  List<ProducerMsg> list= msgMapper.selectByStatus(status);
		  for(ProducerMsg msg:list) {
			 ProducerDto dto= new ProducerDto();
			 dto.setExchange(msg.getExchange());
			 dto.setMsgKey(msg.getMsgKey());
			 dto.setRouterKey(msg.getRouterKey());
			 dto.setMsgContent(msg.getMsgContent());
			 dto.setBizClassName(msg.getBizclassName());
			 dto.setMsgContent(msg.getMsgContent());
			 producerdtolist.add(dto);
		  }
		  return producerdtolist;
	}

}
```


使用mom的的client进行消息发送:   

```
	@Autowired
	private MqSender mqSender;
	
	mqSender.send("event-exchange", "login", msg);

```
demo中的例子

```
	@Autowired
	private MqSender mqSender;
	
	@Autowired
	private BizDao bizDao;
	
	public String saveLoginUser(String name) throws InterruptedException{
		
		1.执行业务
		BizEntity e = new BizEntity();
		e.setId(name);
		e.setName(name);
		bizDao.save(e);
		
		2.组装和发送消息
		LoginMsg msg = new LoginMsg();
		msg.setLoginName(name);
		msg.setLoginTime(new Date().getTime());
		
		mqSender.send("event-exchange", "login", msg);
		
		3.执行其他业务
		return "1";
	}

```




## 消费者使用说明

### 如何配置
---

配置listener-container
```	
    <!--消息接收者-->
<bean id="pointsListenLogin" class="com.yonyou.cloud.mom.demo.msg.listener.PointsListenLogin"></bean>  
  
<!-- queue litener-->  
 <rabbit:listener-container connection-factory="connectionFactory" acknowledge="manual" concurrency="5" prefetch="5">  
         <rabbit:listener queues="pointsListenLoginQueue" ref="pointsListenLogin"/>  
         <rabbit:listener queues="userLogoutQueue" ref="pointsListenLogin"/>  
</rabbit:listener-container>

```	
参数介绍:

acknowledge:消息确认方式。

concurrency:设置listener在初始化的时候并发消费者的个数。

prefetch:每次从队列取出待消费的消息的个数

---

### 如何使用

consumer需实存储DB的回调接口

```
@Service
@Transactional
public class DemoMsgConsumerCallBack implements ConsumerStoreDbCallback{

	@Autowired
	ConsumerMsgMapper consumerMsgMapper;

	@Override
	public boolean exist(String msgKey) throws StoreDBCallbackException {
		ConsumerMsg consumerMsg = consumerMsgMapper.selectByKey(msgKey);
		if(consumerMsg != null ){
			return true;
		}
		return false;
	}

	@Override
	public boolean isProcessing(String msgKey) throws StoreDBCallbackException {
		List<ConsumerMsg> consumerMsgs = consumerMsgMapper.findByMsgKeyAndStatus(msgKey, StoreStatusEnum.CONSUMER_PROCESS.getValue());
		if(consumerMsgs==null || consumerMsgs.size() == 0){
			return false;
		}
		return true;
	}


	@Override
	public void updateMsgSuccess(String msgKey) throws StoreDBCallbackException {
		ConsumerMsg msg = consumerMsgMapper.selectByKey(msgKey);
		if(msg!=null){
			msg.setStatus(StoreStatusEnum.CONSUMER_SUCCESS.getValue());
			msg.setUpdateTime(new Date());
			consumerMsgMapper.updateByKey(msg);
		}else{
			throw new StoreDBCallbackException("can not find msg "+msgKey);
		}
	}

	@Override
	public void updateMsgFaild(String msgKey) throws StoreDBCallbackException {
		ConsumerMsg msg = consumerMsgMapper.selectByKey(msgKey);
		if(msg!=null){
			msg.setStatus(StoreStatusEnum.CONSUMER_FAILD.getValue());
			msg.setUpdateTime(new Date());
			consumerMsgMapper.updateByKey(msg);
		}else{
			throw new StoreDBCallbackException("can not find msg "+msgKey);
		}
		
	}

	@Override
	public void updateMsgProcessing(String msgKey, String data, String exchange, String routerKey,
			String consumerClassName, String bizClassName) throws StoreDBCallbackException {
		ConsumerMsg msg = consumerMsgMapper.selectByKey(msgKey);
		if(msg==null){
			ConsumerMsg msgnew=new ConsumerMsg();
			msgnew.setMsgKey(msgKey);
			msgnew.setCreateTime(new Date());
			msgnew.setStatus(StoreStatusEnum.CONSUMER_PROCESS.getValue());
			msgnew.setUpdateTime(new Date());
			msgnew.setMsgContent(data);
			msgnew.setExchange(exchange);
			msgnew.setRouterKey(routerKey);
			msgnew.setRetryCount(0);
			msgnew.setBizclassName(bizClassName);
			msgnew.setConsumerClassName(consumerClassName);
			consumerMsgMapper.insert(msgnew);
		}else{
			throw new StoreDBCallbackException("can not find msg "+msgKey);
		}
		
	}

	@Override
	public List<ConsumerDto> selectReConsumerList(Integer status) {
		  List<ConsumerDto> producerdtolist=new ArrayList<>();
		  List<ConsumerMsg> list= consumerMsgMapper.findByMsgKeyAndStatus(null, status);
		  for(ConsumerMsg msg:list) {
			 ConsumerDto dto= new ConsumerDto();
			 dto.setMsgKey(msg.getMsgKey());
			 dto.setMsgContent(msg.getMsgContent());
			 dto.setBizClassName(msg.getBizclassName());
			 dto.setMsgContent(msg.getMsgContent());
			 dto.setConsumerClassName(msg.getConsumerClassName());
			 producerdtolist.add(dto);
		  }
		  return producerdtolist;
	}

}

```
实现AbstractConsumerListener<Data extends Object>
例如：

```
@Service
public class PointsListenLogin extends AbstractConsumerListener<LoginMsg>{
	
	private static int counter = 0;

	@Override
	public void handleMessage(LoginMsg data) {
		LOGGER.debug("监听到有人登录了，用户名："+data.getLoginName()+"，发送积分");
		LOGGER.debug("count:{}",++counter);
	}
	
}
```
说明：消费逻辑写在handleMessage中。如果handleMessage发生异常，则会重试消费。

---
