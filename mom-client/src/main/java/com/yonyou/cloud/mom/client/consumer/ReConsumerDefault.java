package com.yonyou.cloud.mom.client.consumer;

public interface ReConsumerDefault {
	/**
	 * 重新消费所有失败的信息
	 * @throws Exception
	 */
	public void reConsumerAllFail () throws Exception ;
	
	/**
	 * 根据key重新消费信息
	 * @param msgKey
	 * @throws Exception
	 */
	public void reConsumerOne (String msgKey) throws Exception ;
}
