package com.yonyou.cloud.mom.core.dto;

public class ConsumerDto {
	
	private String msgKey;
	private String msgContent;
	private Integer status;
	private String infoMsg;
	private Integer retryCount;
	private String consumerClassName;//消费者类名
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

	public String getInfoMsg() {
		return infoMsg;
	}

	public void setInfoMsg(String infoMsg) {
		this.infoMsg = infoMsg;
	}

	public Integer getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(Integer retryCount) {
		this.retryCount = retryCount;
	}

	public String getConsumerClassName() {
		return consumerClassName;
	}

	public void setConsumerClassName(String consumerClassName) {
		this.consumerClassName = consumerClassName;
	}

	public String getBizClassName() {
		return bizClassName;
	}

	public void setBizClassName(String bizClassName) {
		this.bizClassName = bizClassName;
	}


}
