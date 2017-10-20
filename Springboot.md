# 在Springboot的项目中使用MOM组件

## 生产者使用说明

#### 如何配置

1.添加依赖

```
<dependency>
	<groupId>com.yonyou.cloud</groupId>
	<artifactId>mom-client</artifactId>
	<version>{client.version}</version>
</dependency>
```
2.java config

```
@Configuration 
@ComponentScan(basePackages="com.yonyou.cloud.mom")
public class MqConfig {

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
	public ProducerMsgStore getDbStoreProducerMsg() {
		return new DbStoreProducerMsg();
	}
}
	
```	

3.实现回调函数实现消息的存储

```
@Service
@Transactional
public class DemoMsgProducerCallBack implements ProducerStoreDBCallback {

	@Autowired
	MsgDao msgDao;

	@Override
	public void saveStatusData(String msgKey, String data, String exchange, String routerKey)
			throws StoreDBCallbackException {
		System.out.println("进入存储消息的逻辑" + msgKey);
		MsgEntity msg = new MsgEntity();
		msg.setMsgKey(msgKey);
		msg.setExchange(exchange);
		msg.setRouterKey(routerKey);
		msg.setStatus(StoreStatusEnum.PRODUCER_INIT.getValue());
		msg.setRetryCount(0);
		msg.setCreateTime(new Date().getTime());
		msgDao.save(msg);

	}

	@Override
	public void update2InitFailed(String msgKey, String infoMsg, Long costTime) throws StoreDBCallbackException {
		System.out.println("进入消息发送失败的逻辑" + msgKey);
		MsgEntity msg = new MsgEntity();
		msg.setMsgKey(msgKey);
		msg.setStatus(StoreStatusEnum.PRODUCER_FAILD.getValue());
		msg.setInfoMsg(infoMsg);
		msg.setUpdateTime(new Date().getTime());
		msgDao.save(msg);
	}

}

```



#### 如何使用

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

### 注意事项



## 消费者使用说明

### 如何配置

### 如何使用

### 注意事项

### 说明