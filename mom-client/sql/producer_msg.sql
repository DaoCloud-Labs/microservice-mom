DROP TABLE IF EXISTS `producer_msg`;
CREATE TABLE `producer_msg` (
  `msg_key` varchar(200) NOT NULL DEFAULT '' COMMENT '消息key',
  `biz_class_name` varchar(100) NOT NULL DEFAULT '' COMMENT '业务类名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `exchange` varchar(50) NOT NULL DEFAULT '' COMMENT '交换机名称',
  `info_msg` varchar(1000) DEFAULT NULL COMMENT '消息异常信息',
  `msg_content` mediumtext NOT NULL COMMENT '消息体内容',
  `retry_count` int(2) DEFAULT NULL COMMENT '发送次数',
  `router_key` varchar(20) NOT NULL DEFAULT '' COMMENT '消息队列名称',
  `status` int(1) NOT NULL COMMENT '消息状态：0 消息发送初始化,1 消息发送成功,2消息发送失败',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`msg_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
