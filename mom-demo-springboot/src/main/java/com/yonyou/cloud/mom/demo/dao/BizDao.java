package com.yonyou.cloud.mom.demo.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yonyou.cloud.mom.demo.msg.entity.BizEntity;

@Transactional
@Repository
public interface BizDao extends JpaRepository<BizEntity, String>{

}
