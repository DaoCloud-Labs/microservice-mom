package com.yonyou.cloud.mom.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.yonyou.cloud.mom.demo.msg.entity.ConsumerEntity;
import com.yonyou.cloud.mom.demo.msg.entity.MsgEntity;

@Repository
@Transactional
public interface ConsumerDao extends JpaRepository<ConsumerEntity, String>{

	@Query("select m from ConsumerEntity m where m.status=?1")
	public List<ConsumerEntity> findbystatus(Integer status);

	public ConsumerEntity findByMsgKeyAndStatus(String msgKey , Integer status);
 
}


 
