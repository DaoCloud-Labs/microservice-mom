package com.yonyou.cloud.mom.client.producer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitGatewaySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.yonyou.cloud.mom.client.config.AddressConfig;
import com.yonyou.cloud.mom.core.dto.ProducerDto;
import com.yonyou.cloud.mom.core.store.ProducerMsgStore;
import com.yonyou.cloud.mom.core.store.StoreStatusEnum;
import com.yonyou.cloud.mom.core.transaction.executor.AfterCommitExecutorDefaultImpl;
import com.yonyou.cloud.mom.core.transaction.executor.PreCommitExecutorDefaultImpl;
import com.yonyou.cloud.mom.core.transaction.executor.TransactionExecutor;
import com.yonyou.cloud.track.Track;
import net.sf.json.JSONObject;

/**
 * 发送mq的默认实现
 * 
 * @author BENJAMIN
 *
 */
@Service
public class MqSenderDefaultImpl extends RabbitGatewaySupport implements MqSender{

	protected final Logger LOGGER = LoggerFactory.getLogger(MqSenderDefaultImpl.class);

	// 事务提交前的执行类
	private TransactionExecutor beforeCommitExecutor = new PreCommitExecutorDefaultImpl();

	// 事务提交后的执行类
	private TransactionExecutor afterCommitExecutor = new AfterCommitExecutorDefaultImpl();

	// msg Db Store的实现
	@Autowired
	private ProducerMsgStore msgStore ; 
	
	
	
	private Track tack; 
	
	
	
	public Track getTack() {
		return tack;
	}

	public void setTack(Track tack) {
		this.tack = tack;
	}



	@Value("${track.isTacks:false}")
	private Boolean isTacks;  
	
	@Autowired
	AddressConfig address;
	
	@Override
	@Transactional
	public void send(String exchange, String routeKey, Object data, String ...bizCodes) {
		
		// 消息主键
		StringBuffer msgKey=new StringBuffer(); 
		msgKey.append(UUID.randomUUID().toString());
		msgKey.append(StringUtils.isEmpty(StringUtils.join(bizCodes, "&"))?"":"&"+StringUtils.join(bizCodes, "&"));
		
		ObjectMapper mapper = new ObjectMapper();
		// 转换后的String
		String dataConvert;
		try {
			// convert
			dataConvert = mapper.writeValueAsString(data);

			// store
			storeMsg(dataConvert, msgKey.toString(), exchange, routeKey,data.getClass().getName());

		} catch (IOException e) {
			throw new AmqpException(e);
		} 
			sendToMQ(exchange, routeKey, msgKey.toString(), data);
	}

	@Override
	public void justSend(String exchange, String routeKey, Object data) {
		sendRabbitQ(exchange,routeKey, "",data);
	}

	private void sendToMQ(final String exchange, final String routeKey, final String msgKey, final Object data) {
		afterCommitExecutor.execute(new Runnable() {
			
			@Override
			public void run() {
				
				ObjectMapper mapper = new ObjectMapper();
				// 转换后的String
				String dataConvert;
				try {
					dataConvert = mapper.writeValueAsString(data);
				} catch (IOException e1) {
					throw new AmqpException(e1);
				}

				Long startTime = 0L;
				try {
					LOGGER.debug("------发送消息开始------");
					startTime = System.currentTimeMillis();
					sendRabbitQ(exchange,routeKey, msgKey, data);
					msgStore.update2success(msgKey);
					//消息发送成功埋点
					try {
						if(isTacks) {
							Map<String, Object> properties=new HashMap<>();
							properties.put("type", "PRODUCER");
							properties.put("msgKey", msgKey); 
							properties.put("sender", data.getClass().getName()); 
							properties.put("exchangeName",exchange); 
							properties.put("routingKey", routeKey!=null?routeKey:""); 
							properties.put("data", dataConvert); 
							properties.put("success", "true"); 
							properties.put("host", address.applicationAndHost().get("hostIpAndPro"));
							properties.put("serviceUrl",address.applicationAndHost().get("applicationAddress"));
							tack.track("msgProducer", "mqTrack", properties);
							tack.shutdown();
						}
					} catch (Exception e1) {
						LOGGER.error("埋点msgProducer 发生异常",e1);
					}
					
				} catch (Exception e) {
					// 设置为失败
					LOGGER.debug("------发送消息异常，调用消息存储失败的方法------");
					
					msgStore.msgStoreFailed(msgKey, e.getMessage(), System.currentTimeMillis() - startTime, exchange,  routeKey,dataConvert,data.getClass().getName());
					//消息发送失败埋点 
					try {
						if(isTacks) {
							Map<String, Object> properties=new HashMap<>();
							properties.put("type", "PRODUCER");
							properties.put("msgKey", msgKey.toString()); 
							properties.put("sender", data.getClass().getName()); 
							properties.put("serviceUrl",address.applicationAddress());
							properties.put("exchangeName",exchange);
							properties.put("routingKey", routeKey!=null?routeKey:"");
							properties.put("data", dataConvert);
							properties.put("success", "false"); 
							properties.put("host", address.applicationAndHost().get("hostIpAndPro"));
							properties.put("serviceUrl",address.applicationAndHost().get("applicationAddress"));
							properties.put("infoMsg", e.getMessage());
							tack.track("msgProducer", "mqTrack", properties);
							tack.shutdown();
						}
					} catch (Exception e1) {
						LOGGER.error("埋点msgProducer 发生异常",e1);
					}
					throw e;
				}
			}
		});
	}
	
	
	protected void sendRabbitQ(String exchange,String routeKey, String correlation, Object data) {

		getRabbitOperations().convertAndSend(exchange,routeKey, data, new MessagePostProcessor() {

			@Override
			public Message postProcessMessage(Message message) throws AmqpException {

                try {
//                    message.getMessageProperties().setCorrelationIdString(correlation);
                	 message.getMessageProperties().setCorrelationId(correlation);
                    message.getMessageProperties().setContentType("json");
                   
                } catch (Exception e) {
                    throw new AmqpException(e);
                }
              
                return message;
                
            }
        });
		
		LOGGER.debug("------消息发送完成------");
    }

	/**
	 * 存储消息
	 * 
	 * @param data
	 * @param msgKey
	 * @param exchange
	 * @param routerKey
	 * @throws IOException
	 */
	private void storeMsg(final String data, final String msgKey, final String exchange, final String routerKey,String bizClassName)
			throws IOException {

		// save data before commit
		beforeCommitExecutor.execute(new Runnable() {
			@Override
			public void run() {
				msgStore.msgStore(msgKey, data, exchange, routerKey,bizClassName);
			}
		});
	}
	
	@Override
	public void resend(String ...msgKeys) {
		if(msgKeys.length>0) {
			List<ProducerDto> list = new ArrayList<>();
			for(String msgKey: msgKeys){
				ProducerDto dto=msgStore.getResendDto(msgKey);
				list.add(dto); 
			}
			sendListToMQ(list);
		}else {
			List<ProducerDto> list = msgStore.selectResendList(StoreStatusEnum.PRODUCER_FAILD.getValue());
			sendListToMQ(list);
		}
	}
	
 

	public void sendListToMQ(List<ProducerDto> list) {
		Iterator<ProducerDto> it = list.iterator();
		while (it.hasNext()) {
			ProducerDto msgEntity = it.next();
			LOGGER.info(msgEntity.getMsgContent() + "消息内容");
			try {
				Class<?> c = Class.forName(msgEntity.getBizClassName());
				JSONObject obj = JSONObject.fromObject(msgEntity.getMsgContent());
				Object ojbClass = JSONObject.toBean(obj, c);

				sendToMQ(msgEntity.getExchange(), msgEntity.getRouterKey(), msgEntity.getMsgKey(), ojbClass);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
