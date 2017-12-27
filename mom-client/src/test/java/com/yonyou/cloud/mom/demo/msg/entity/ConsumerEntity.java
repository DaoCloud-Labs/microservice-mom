package com.yonyou.cloud.mom.demo.msg.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@DynamicInsert
@Table(name="consumer_msg")
public class ConsumerEntity {
	
	public ConsumerEntity() {
	}

	@Id
	@Column(length = 100)
	private String msgKey;
	
	@Column(columnDefinition = "mediumtext")
	private String msgContent;
	
	@Column(nullable = true)
	private Integer status;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;
	
	@Column(nullable = true)
	private String exchange;
	
	@Column(nullable = true)
	private String routerKey;
	
	private String infoMsg;
	
	private Integer retryCount;
	
	@Column(nullable = true)
	private String consumerClassName;//消费者类名
	
	@Column(nullable = true)
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
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

	public String getConsumerClassName() {
		return consumerClassName;
	}

	public void setConsumerClassName(String consumerClassName) {
		this.consumerClassName = consumerClassName;
	}
	
	
	

}
