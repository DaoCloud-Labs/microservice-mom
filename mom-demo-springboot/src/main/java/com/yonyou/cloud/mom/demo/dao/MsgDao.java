package com.yonyou.cloud.mom.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.yonyou.cloud.mom.demo.msg.entity.MsgEntity;

@Repository
@Transactional
public interface MsgDao extends JpaRepository<MsgEntity, String>{
	
	
	public MsgEntity findByMsgKeyAndStatus(String msgKey , Integer status);

	@Query("select m from MsgEntity m where m.status=?1")
	public List<MsgEntity> findbystatus(Integer status); 
 
}


 
