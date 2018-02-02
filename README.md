# microservice-mom

基于RabbitMQ实现的分布式可靠的消息组件


## 组件目标
* 解决消息队列和本地事务的分布式事务一致的问题

## 说明

**默认事务发生后一定成功的条件下来保证事务一致性，不支持分布式事务回滚** 

*注：如果需要解决分布式事务自动回滚，可以让发生回滚的服务发送回滚消息给   
其他服务进行回滚来解决*



## 设计思路来源
[如何消息必达](https://mp.weixin.qq.com/s?__biz=MjM5ODYxMDA5OQ==&mid=2651959966&idx=1&sn=068a2866dcc49335d613d75c4a5d1b17&chksm=bd2d07428a5a8e54162ad8ea8e1e9302dfaeb664cecc453bd16a5f299820755bd2e1e0e17b60&scene=21#wechat_redirect) 

## 项目信息
java:1.8   
SpringBoot:1.5.7  
Spring:4.0+  


## 使用方法

[springboot使用说明](https://github.com/yonyou-auto-dev/microservice-mom/blob/dev/springbootdemo.md)

[springmvc使用说明](https://github.com/yonyou-auto-dev/microservice-mom/blob/dev/Springmvc.md)




## 版本说明
```
mom-cli 0.03版本说明
	1.新增了重新发送接口
	   1.1支持通过http请求单条重新发送
	   1.2支持定时器自动扫描需要重发的信息
	2.新增了重新消费接口
	   2.1支持通过http请求单条重新发送
	   2.2支持定时器自动扫描需要消费的信息
	3.修改了发送者存储DB的回调接口 ProducerStoreDBCallback
		3.1修改查询多条重新发送 的方法名
		3.2 新增根据msgKey 查询单条重发消息的方法
	4.修改了消费者存储DB的回调接口  StoreDBCallbackException
		4.1 exist() 根据msgkey判断消息是否存在{true：存在,false:不存在}
		4.2 saveMsgData()保存接受到的信息
		4.3 新增更加msgKey 查询单条需要重新消费的信息
	5.优化了重新消费的逻辑
		目前重新消费通过Java反射方式实现
	6.优化了埋点相关属性
```
