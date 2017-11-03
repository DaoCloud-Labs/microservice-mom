package com.yonyou.cloud.mom.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yonyou.cloud.mom.demo.entity.ProducerMsg;

	public interface ProducerMsgMapper  {

		void insert(ProducerMsg producerMsg);
 
		ProducerMsg selectByKey(@Param("msgKey")String msgKey);

		void updateByKey(ProducerMsg producerMsg);

		List<ProducerMsg> selectByStatus(@Param("status")Integer status);
}