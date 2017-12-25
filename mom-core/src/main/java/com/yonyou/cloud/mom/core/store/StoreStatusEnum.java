package com.yonyou.cloud.mom.core.store;

/**
 * 消息状态枚举
 * 
 * @author BENJAMIN
 *
 */
public enum StoreStatusEnum {
	
	/**
	 * 生产消息初始化
	 */
	PRODUCER_INIT(0),
	/**
	 * 消息发送成功
	 */
	PRODUCER_SUCCESS(1),
	/**
	 * 消息发送失败
	 */
	PRODUCER_FAILD(2),
	/**
	 * 消费者消费中
	 */
	CONSUMER_PROCESS(100),
	/**
	 * 消费成功
	 */
	CONSUMER_SUCCESS(101),
	/**
	 * 消费失败
	 */
	CONSUMER_FAILD(102);
	

	
	private final int value;

	StoreStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
