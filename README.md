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

sql脚本

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for consumer_msg
-- ----------------------------
DROP TABLE IF EXISTS `consumer_msg`;
CREATE TABLE `consumer_msg` (
  `msg_key` varchar(255) NOT NULL DEFAULT '' COMMENT '消息key',
  `biz_class_name` varchar(255) NOT NULL DEFAULT '' COMMENT '业务类名称',
  `consumer_class_name` varchar(255) NOT NULL DEFAULT '' COMMENT '监听类名称',
  `create_time` datetime DEFAULT NULL COMMENT '消息接受时间',
  `exchange` varchar(255) NOT NULL DEFAULT '' COMMENT '交换机名称',
  `info_msg` varchar(255) DEFAULT NULL COMMENT '消息异常信息',
  `msg_content` mediumtext NOT NULL COMMENT '消息体内容',
  `retry_count` int(11) DEFAULT NULL COMMENT '消费次数',
  `router_key` varchar(255) NOT NULL DEFAULT '' COMMENT '消息队列名称',
  `status` int(11) NOT NULL  COMMENT '消息状态：100 消息消费初始化,101 消息消费成功,102 消息消费失败',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`msg_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for producer_msg
-- ----------------------------
DROP TABLE IF EXISTS `producer_msg`;
CREATE TABLE `producer_msg` (
  `msg_key` varchar(255) NOT NULL DEFAULT '' COMMENT '消息key',
  `biz_class_name` varchar(255) NOT NULL DEFAULT '' COMMENT '业务类名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `exchange` varchar(255) NOT NULL DEFAULT '' COMMENT '交换机名称',
  `info_msg` varchar(255) DEFAULT NULL COMMENT '消息异常信息',
  `msg_content` mediumtext NOT NULL COMMENT '消息体内容',
  `retry_count` int(11) DEFAULT NULL COMMENT '发送次数',
  `router_key` varchar(255) NOT NULL DEFAULT '' COMMENT '消息队列名称',
  `status` int(11) NOT NULL COMMENT '消息状态：0 消息发送初始化,1 消息发送成功,2消息发送失败',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`msg_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET FOREIGN_KEY_CHECKS=1;

