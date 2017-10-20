package com.yonyou.cloud.mom.core.store.callback;

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
    void saveStatusData(String msgKey, String data, String exchange, String routerKey)
        throws StoreDBCallbackException;

    /**
     * 处理为初始化失败
     *
     * @param correlation
     * @param infoMsg
     *
     * @throws StoreUserCallbackException
     */
    void update2InitFailed(String msgKey, String infoMsg, Long costTime) throws StoreDBCallbackException;

}
