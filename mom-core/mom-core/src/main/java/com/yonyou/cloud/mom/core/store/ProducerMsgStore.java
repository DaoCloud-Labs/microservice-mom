package com.yonyou.cloud.mom.core.store;

import java.util.List;

import com.yonyou.cloud.mom.core.dto.ProducerDto;
import com.yonyou.cloud.mom.core.store.callback.exception.StoreException;

/**
 * @author BENJAMIN
 * 
 *
 */
public interface ProducerMsgStore {

    void msgStore(String msgKey, String data, String exchange, String routerKey,String bizClassName) throws StoreException;

    /**
     * 处理为失败
     *
     * @param correlation
     * @param infoMsg
     *
     * @throws StoreException
     */
    void msgStoreFailed(String msgKey, String infoMsg, Long costTime) throws StoreException;
    
    /**
     * 发送成功处理
     * @param msgKey
     * @throws StoreException
     */
    void update2success(String msgKey) throws StoreException;
     
    /**
     * 扫描需要重新发送的消息
     * @param status
     * @return
     */
    List<ProducerDto> selectResendList(Integer status);
}