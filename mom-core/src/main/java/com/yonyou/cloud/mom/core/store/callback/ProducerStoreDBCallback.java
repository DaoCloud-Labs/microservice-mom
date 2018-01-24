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
     * @param correlation
     * @param data
     */
    void saveMsgData(String msgKey, String data, String exchange, String routerKey,String bizClassName)
        throws StoreDBCallbackException;
    
    /**
     * 发送成功
     * @param originalMsgKey
     * @throws StoreDBCallbackException
     */
    void update2success(String msgKey)throws StoreDBCallbackException;

    /**
     * 处理为初始化失败
     *
     * @param correlation
     * @param infoMsg
     *
     * @throws StoreUserCallbackException
     */
    void update2faild(String msgKey, String infoMsg, Long costTime,String exchange, String routerKey,String data,String bizClassName) throws StoreDBCallbackException;
    
    /**
     * 获取需要重新发送的内容
     * @param status
     * @return
     */
    public List<ProducerDto> selectResendList(Integer status);
    
    /**
     * 重置发送失败的次数
     * @param msgKey
     */
    public Boolean resetErrorCount(String msgKey);
}
