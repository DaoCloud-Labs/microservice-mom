package com.yonyou.cloud.mom.core.dto;

/**
 * 发送的dto
 */
public class ProducerDto {
	private String msgKey;
	private String msgContent;
	private Integer status;
	private String exchange;
	private String routerKey;
	private String bizClassName;
	
	public String getMsgKey() {
		return msgKey;
	}
	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getExchange() {
		return exchange;
	}
	public void setExchange(String exchange) {
		this.exchange = exchange;
	}
	public String getRouterKey() {
		return routerKey;
	}
	public void setRouterKey(String routerKey) {
		this.routerKey = routerKey;
	}
	public String getBizClassName() {
		return bizClassName;
	}
	public void setBizClassName(String bizClassName) {
		this.bizClassName = bizClassName;
	}
  
}
