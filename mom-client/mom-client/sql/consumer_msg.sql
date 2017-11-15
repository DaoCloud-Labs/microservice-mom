CREATE TABLE `consumer_msg` (
  `msg_key` varchar(255) NOT NULL DEFAULT '' COMMENT '消息key',
  `biz_class_name` varchar(255) NOT NULL COMMENT '业务类名称',
  `consumer_class_name` varchar(255) NOT NULL COMMENT '监听类名称',
  `create_time` datetime DEFAULT NULL COMMENT '消息接受时间',
  `exchange` varchar(255) NOT NULL COMMENT '交换机名称',
  `info_msg` varchar(255) DEFAULT NULL COMMENT '消息异常信息',
  `msg_content` mediumtext NOT NULL COMMENT '消息体内容',
  `retry_count` int(11) DEFAULT NULL COMMENT '消费次数',
  `router_key` varchar(255) NOT NULL COMMENT '消息队列名称',
  `status` int(11) NOT NULL COMMENT '消息状态：100 消息消费初始化,101 消息消费成功,102 消息消费失败',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`msg_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

