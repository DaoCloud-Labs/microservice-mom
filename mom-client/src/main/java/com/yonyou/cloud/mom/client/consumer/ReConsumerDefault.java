package com.yonyou.cloud.mom.client.consumer;
/**
 * 
 * @author daniell
 *
 */
public interface ReConsumerDefault {
	/**
	 * 重新消费失败的信息
	 * @param msgKey
	 * @throws Exception
	 */
	public void reConsumer( String... msgKey) throws Exception;
}
