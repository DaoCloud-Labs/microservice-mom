package com.yonyou.cloud.mom.core.store.callback;

import java.util.List;

import com.yonyou.cloud.mom.core.dto.ProducerDto;
import com.yonyou.cloud.mom.core.store.callback.exception.StoreDBCallbackException;

/**
 * @author BENJAMIN
 * 存储DB的回调接口
 */
public interface ProducerStoreDBCallback{


    /**
     * 保存消息
     * @param msgKey
     * @param data
     * @param exchange
     * @param routerKey
     * @param bizClassName
     * @throws StoreDBCallbackException
     */
    void saveMsgData(String msgKey, String data, String exchange, String routerKey,String bizClassName)
        throws StoreDBCallbackException;
    
    /**
     * 发送成功后相关处理
     * @param originalMsgKey
     * @throws StoreDBCallbackException
     */
    void update2success(String msgKey)throws StoreDBCallbackException;

    /**
     * 发送失败后相关处理
     * @param msgKey
     * @param infoMsg
     * @param costTime
     * @param exchange
     * @param routerKey
     * @param data
     * @param bizClassName
     * @throws StoreDBCallbackException
     */
    void update2faild(String msgKey, String infoMsg, Long costTime,String exchange, String routerKey,String data,String bizClassName) throws StoreDBCallbackException;
    
    /**
     * 获取需要重新发送的内容
     * @param status
     * @return
     */
    public List<ProducerDto> selectResendList(Integer status);
    
    
    /**
     * 根据msgKey获取单条需要重新发送的消息
     * @param msgkey
     * @return
     */
    public ProducerDto getResendProducerDto(String msgkey);
    
 
}
