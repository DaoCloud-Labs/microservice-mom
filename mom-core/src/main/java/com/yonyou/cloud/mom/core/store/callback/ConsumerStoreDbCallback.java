package com.yonyou.cloud.mom.core.store.callback;

import java.util.List;

import com.yonyou.cloud.mom.core.dto.ConsumerDto;
import com.yonyou.cloud.mom.core.store.callback.exception.StoreDBCallbackException;

/**
 * 消费者需要实现的回调
 * 
 * @author BENJAMIN
 *
 */
public interface ConsumerStoreDbCallback {
 

    /**
     * 根据msgkey判断消息是否存在{true：存在,false:不存在}
     * 
     * @param msgKey
     * @throws StoreDBCallbackException
     */
    boolean exist(String msgKey) throws StoreDBCallbackException;
    
    
    /**
     * 保存接受到的信息
     * 
     * @param msgKey
     * @throws StoreDBCallbackException
     */
    void saveMsgData(String msgKey,String data,String exchange,String routerKey,String consumerClassName,String bizClassName) throws StoreDBCallbackException;
    
    
    /**
     * 更新为成功
     * 
     * @param msgKey
     * @throws StoreDBCallbackException
     */
    void updateMsgSuccess(String msgKey) throws StoreDBCallbackException;
    
    
    /**
     * 更新为失败
     * 
     * @param msgKey
     * @throws StoreDBCallbackException
     */
    void updateMsgFaild(String msgKey) throws StoreDBCallbackException;
    
    /**
     * 获取需要重新消费的消息
     * @param status
     * @return
     */
    public List<ConsumerDto> selectReConsumerList(Integer status);
    
    
    /**
     * 根据msgKey获取单条需要重新消费的消息
     * @param msgKey
     * @return
     */
    public ConsumerDto getReConsumerDto(String msgKey); 
 
}
