package com.yonyou.cloud.mom.demo.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "producer_msg")
public class ProducerMsg {
    @Id
    @Column(name = "MSG_KEY")
    private String msgKey;

    @Column(name = "MSG_CONTENT")
    private String msgContent;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    @Column(name = "EXCHANGE")
    private String exchange;

    @Column(name = "ROUTER_KEY")
    private String routerKey;

    @Column(name = "INFO_MSG")
    private String infoMsg;

    @Column(name = "RETRY_COUNT")
    private Integer retryCount;

    @Column(name = "BIZ_CLASS_NAME")
    private String bizclassName;

    /**
     * @return MSG_KEY
     */
    public String getMsgKey() {
        return msgKey;
    }

    /**
     * @param msgKey
     */
    public void setMsgKey(String msgKey) {
        this.msgKey = msgKey;
    }

    /**
     * @return MSG_CONTENT
     */
    public String getMsgContent() {
        return msgContent;
    }

    /**
     * @param msgContent
     */
    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    /**
     * @return STATUS
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return CREATE_TIME
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return UPDATE_TIME
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return EXCHANGE
     */
    public String getExchange() {
        return exchange;
    }

    /**
     * @param exchange
     */
    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    /**
     * @return ROUTER_KEY
     */
    public String getRouterKey() {
        return routerKey;
    }

    /**
     * @param routerKey
     */
    public void setRouterKey(String routerKey) {
        this.routerKey = routerKey;
    }

    /**
     * @return INFO_MSG
     */
    public String getInfoMsg() {
        return infoMsg;
    }

    /**
     * @param infoMsg
     */
    public void setInfoMsg(String infoMsg) {
        this.infoMsg = infoMsg;
    }

    /**
     * @return RETRY_COUNT
     */
    public Integer getRetryCount() {
        return retryCount;
    }

    /**
     * @param retryCount
     */
    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    /**
     * @return BIZ_CLASS_NAME
     */
    public String getBizclassName() {
        return bizclassName;
    }

    /**
     * @param bizclassName
     */
    public void setBizclassName(String bizclassName) {
        this.bizclassName = bizclassName;
    }
}