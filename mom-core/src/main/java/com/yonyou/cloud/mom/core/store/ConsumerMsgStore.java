package com.yonyou.cloud.mom.core.store;

import java.util.List;

import com.yonyou.cloud.mom.core.dto.ConsumerDto;
import com.yonyou.cloud.mom.core.store.callback.exception.StoreException;

/**
 * 消费者的消息存储接口
 * 
 * @author BENJAMIN
 *
 */
public interface ConsumerMsgStore {

    /**
     * 根据msgkey判断消息是否已经存在
     * 
     * @param msgKey
     * @throws StoreException
     */
    boolean exist(String msgKey) throws StoreException;

    /**
     * 根据msgkey判断消息是否在处理中
     * 
     * @param msgKey
     * @throws StoreException
     */
    boolean isProcessing(String msgKey) throws StoreException;
    
    
    /**
     * 更新消息状态为处理中
     * 
     * @param msgKey
     * @throws StoreException
     */
    void updateMsgProcessing(String msgKey,String data,String exchange,String routerKey,String consumerClassName,String bizClassName) throws StoreException;
    
    
    /**
     * 更新为成功
     * 
     * @param msgKey
     * @throws StoreException
     */
    void updateMsgSuccess(String msgKey) throws StoreException;
    
    
    /**
     * 更新为失败
     * 
     * @param msgKey
     * @throws StoreException
     */
    void updateMsgFaild(String msgKey) throws StoreException;
    
    
    /**
     * 获取需要重新消费的信息
     * @param status
     * @return
     */
    public List<ConsumerDto> selectReConsumerList(Integer status);
}