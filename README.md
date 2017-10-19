# microservice-mom

基于RabbitMQ实现的分布式可靠的消息组件


## 目标
解决分布式中业务与消息一致性的问题   
1.发送一致性(进度80%)   
2.消费一致性(进度1%)  

## 设计思路来源
[如何消息必达](https://mp.weixin.qq.com/s?__biz=MjM5ODYxMDA5OQ==&mid=2651959966&idx=1&sn=068a2866dcc49335d613d75c4a5d1b17&chksm=bd2d07428a5a8e54162ad8ea8e1e9302dfaeb664cecc453bd16a5f299820755bd2e1e0e17b60&scene=21#wechat_redirect) 

## 组件版本
java:1.8  
springboot:1.5.7 

## 使用方法-springboot

### Step1：pom.xml 文件中增加

```
<dependency>
	<groupId>com.yonyou.cloud</groupId>
	<artifactId>mom-client</artifactId>
	<version>{client.version}</version>
</dependency>
```
### Step2 ：配置

```
@Configuration
public class MqConfig {

	@Bean
	public MqSenderDefaultImpl mqSenderDefaultImpl(RabbitOperations rabbitOperations){
		MqSenderDefaultImpl mqSenderDefaultImpl = new MqSenderDefaultImpl();
		mqSenderDefaultImpl.setRabbitOperations(rabbitOperations);
		return mqSenderDefaultImpl;
	}
	
	@Bean
	public SpringUtil springUtil(){
		return new SpringUtil();
	}
	
	@Bean
	public ProducerMsgStore getDbStoreProducerMsg(){
		return new DbStoreProducerMsg();
	}
}
```

### Step3: 实现回调 ProducerStoreDBCallback接口：
```
@Service
@Transactional
public class DemoMsgCallBack implements ProducerStoreDBCallback {

	@Autowired
	MsgDao msgDao;

	@Override
	public void saveStatusData(String correlation, String data, String exchange, String routerKey)
			throws StoreDBCallbackException {
		MsgEntity msg = new MsgEntity();
		msg.setId(correlation);
		msg.setMsg(data);
		msg.setIsSend("0");
		msgDao.save(msg);

	}

	@Override
	public void update2InitFailed(String correlation, String infoMsg, Long costTime) throws StoreDBCallbackException {
		System.out.println("进入消息发送失败的逻辑" + correlation);
		MsgEntity msg = new MsgEntity();
		msg.setId(correlation);
		msg.setIsSend("1");
		msgDao.save(msg);
	}

}
```


### Step4: 开始使用

```

@Autowired
private MqSender mqSender;

@Autowired
private BizDao bizDao;
	
@Transactional
public void saveLoginUser(String name) throws InterruptedException{
		
		// 1. 执行业务
		BizEntity e = new BizEntity();
		e.setId(name);
		e.setName(name);
		bizDao.save(e);
		
		// 2.执行发送消息
		mqSender.send("tt", "login", name+"log event");

	}
```

