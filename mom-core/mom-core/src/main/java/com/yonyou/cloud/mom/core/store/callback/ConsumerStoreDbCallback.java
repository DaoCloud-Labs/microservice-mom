package com.yonyou.cloud.mom.core.store.callback;

import com.yonyou.cloud.mom.core.store.callback.exception.StoreDBCallbackException;

/**
 * 消费者需要实现的回调
 * 
 * @author BENJAMIN
 *
 */
public interface ConsumerStoreDbCallback {


    /**
     * 根据msgkey判断消息是否已经存在
     * 
     * @param msgKey
     * @throws StoreDBCallbackException
     */
    boolean exist(String msgKey) throws StoreDBCallbackException;

    /**
     * 根据msgkey判断消息是否在处理中
     * 
     * @param msgKey
     * @throws StoreDBCallbackException
     */
    boolean isProcessing(String msgKey) throws StoreDBCallbackException;
    
    
    /**
     * 更新消息状态为处理中
     * 
     * @param msgKey
     * @throws StoreDBCallbackException
     */
    void updateMsgProcessing(String msgKey) throws StoreDBCallbackException;
    
    
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
}
