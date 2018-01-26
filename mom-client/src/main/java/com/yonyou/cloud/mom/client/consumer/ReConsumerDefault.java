package com.yonyou.cloud.mom.client.consumer;

public interface ReConsumerDefault {
	/**
	 * 重新消费失败的信息
	 * @throws Exception
	 */
	public void reConsumer( String... msgKey) throws Exception;
}
