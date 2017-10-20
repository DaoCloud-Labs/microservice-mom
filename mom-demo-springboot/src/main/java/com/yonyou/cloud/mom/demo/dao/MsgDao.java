package com.yonyou.cloud.mom.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yonyou.cloud.mom.demo.msg.entity.MsgEntity;

public interface MsgDao extends JpaRepository<MsgEntity, String>{
	
	
	public MsgEntity findByMsgKeyAndStatus(String msgKey , Integer status);

}
