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
springboot:1.5.7 

## 使用方法

[springboot使用说明](https://github.com/yonyou-auto-dev/microservice-mom/blob/dev/Springboot.md)

[springmvc使用说明](https://github.com/yonyou-auto-dev/microservice-mom/blob/dev/Springmvc.md)


