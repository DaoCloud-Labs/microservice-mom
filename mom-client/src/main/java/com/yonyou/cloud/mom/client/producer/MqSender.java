package com.yonyou.cloud.mom.client.producer;


public interface MqSender {
	
	/**
	 * 一致性发送：不会丢失消息
	 * 
	 * @param exchange
	 * @param routeKey
	 * @param data
	 */
	void send(String exchange, String routeKey, Object data, String ... bizCodes);
	
	/**
	 * 非一致性发送：极端情况有可能会丢失消息
	 * 
	 * @param exchange
	 * @param routeKey
	 * @param data
	 */
	void justSend(String exchange, String routeKey, Object data);
	
	
	/**
	 * 重发所有失败的信息
	 */
	void reSendAllFail(); 
	
	/**
	 * 根据key重发信息
	 * @param msgKey
	 */
	void reSendOne(String msgKey); 
}
