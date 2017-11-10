package com.yonyou.cloud.mom.demo.msg.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@DynamicInsert
@Table(name="producer_msg")
public class MsgEntity {
	
	public MsgEntity() {
	}

	@Id
	private String msgKey;
	
	private String msgContent;
	
	private Integer status;
	
	private Long createTime;
	
	private Long updateTime;
	
	private String exchange;
	
	private String routerKey;
	
	private String infoMsg;
	
	private Integer retryCount;
	
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

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
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

	@Override
	public String toString() {
		return "MsgEntity [msgKey=" + msgKey + ", msgContent=" + msgContent + ", status=" + status + ", createTime="
				+ createTime + ", updateTime=" + updateTime + ", exchange=" + exchange + ", routerKey=" + routerKey
				+ ", infoMsg=" + infoMsg + ", retryCount=" + retryCount + "]";
	}

	public String getBizClassName() {
		return bizClassName;
	}

	public void setBizClassName(String bizClassName) {
		this.bizClassName = bizClassName;
	}
	
	
	

}
