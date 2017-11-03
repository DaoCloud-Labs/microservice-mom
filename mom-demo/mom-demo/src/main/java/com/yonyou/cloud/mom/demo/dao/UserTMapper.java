package com.yonyou.cloud.mom.demo.dao;

import com.yonyou.cloud.mom.demo.entity.UserT;
import com.yonyou.cloud.mom.demo.entity.UserTExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface UserTMapper {
    
    int selectCnt();
}