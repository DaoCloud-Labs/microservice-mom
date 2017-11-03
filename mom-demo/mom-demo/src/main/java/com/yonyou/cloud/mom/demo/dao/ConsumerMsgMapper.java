package com.yonyou.cloud.mom.demo.dao;

import org.apache.ibatis.annotations.Param;

import com.yonyou.cloud.mom.demo.entity.ConsumerMsg;

public interface ConsumerMsgMapper {
	ConsumerMsg selectByKey(@Param("msgKey")String msgKey);

	ConsumerMsg findByMsgKeyAndStatus(@Param("msgKey")String msgKey, @Param("status")int status);
	
	void updateByKey(ConsumerMsg producerMsg);

	void insert(ConsumerMsg msgnew);

}