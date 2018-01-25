package com.yonyou.cloud.mom.client.consumer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.cloud.mom.client.config.AddressConfig;
import com.yonyou.cloud.mom.core.dto.ConsumerDto;
import com.yonyou.cloud.mom.core.store.ConsumerMsgStore;
import com.yonyou.cloud.mom.core.store.StoreStatusEnum;
import com.yonyou.cloud.track.Track;

import net.sf.json.JSONObject;

@Service
public class ReConsumerDefaultImpl  implements ReConsumerDefault {
	Logger log= LoggerFactory.getLogger(ReConsumerDefaultImpl.class);
	
	@Autowired
	private ConsumerMsgStore msgStore ;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	Track tack;
	
	@Value("${track.isTacks:false}")
	private Boolean isTacks; 
	
	@Autowired
	AddressConfig address;
	
	@Override
	public void reConsumerAllFail() throws  Exception {
		List<ConsumerDto> list=msgStore.selectReConsumerList(StoreStatusEnum.CONSUMER_FAILD.getValue());
		reConsumerExecute(list);

	}
	
	@Override
	public void reConsumerOne(String msgKey) throws  Exception {
		log.info("重新消费"+msgKey);
		ConsumerDto dto=msgStore.selectReConsumerList(msgKey);
		List<ConsumerDto> list=new ArrayList<>();
		list.add(dto);
		reConsumerExecute(list);
	}
	
	public void reConsumerExecute(List<ConsumerDto> list) {
		Iterator<ConsumerDto> it=list.iterator();
		 while (it.hasNext()) {
			 ConsumerDto msgEntity = it.next();
			 log.info(msgEntity.getMsgContent()+"消息内容");			
			 executeReConsumer(msgEntity);
		}
	}
 
	
 
    @Transactional
    @SuppressWarnings("unchecked")
    private void executeReConsumer( ConsumerDto msgEntity) {
		try {
			//创建一个类
			 Class c =Class.forName(msgEntity.getBizClassName()); 
			 JSONObject obj = JSONObject.fromObject(msgEntity.getMsgContent());
			//把json转化成指定的对象
			 Object ojbClass = JSONObject.toBean(obj,c);
			 
			 resendRabbitQ(msgEntity.getExchange(),msgEntity.getRouterKey(),msgEntity.getMsgKey(),  ojbClass); 
 
			 //更新状态
			 msgStore.updateMsgSuccess(msgEntity.getMsgKey());
			 
			 
			   //消息消费成功埋点 
         	try {
         		if(isTacks) { 
					Map<String, Object> properties=new HashMap<>();
					properties.put("type", "CONSUMER");
					properties.put("msgKey", msgEntity.getMsgKey()); 
					properties.put("sender", msgEntity.getBizClassName()); 
					properties.put("exchangeName","");
					properties.put("routingKey", msgEntity.getRouterKey()); 
					properties.put("data", msgEntity.getMsgContent());
					properties.put("consumerId", msgEntity.getConsumerClassName()); 
					properties.put("success", "true"); 
					properties.put("host", address.ApplicationAndHost().get("hostIpAndPro"));
					properties.put("serviceUrl",address.ApplicationAndHost().get("applicationAddress")); 
					properties.put("IsRestart", "true");
					tack.track("msgCustomer", "mqTrack", properties);
					tack.shutdown();
         		}
				} catch (Exception e1) {
					log.info("埋点msgCustomer 发生异常");
				}
         	
		} catch (Exception e) {
            //消息消费失败埋点
			try {
				if(isTacks) {
					Map<String, Object> properties=new HashMap<>();
					properties.put("type", "CONSUMER");
					properties.put("msgKey", msgEntity.getMsgKey()); 
					properties.put("sender", msgEntity.getBizClassName()); 
					properties.put("exchangeName","");
					properties.put("routingKey", msgEntity.getRouterKey()); 
					properties.put("data", msgEntity.getMsgContent());
					properties.put("consumerId", msgEntity.getConsumerClassName()); 
					properties.put("success", "false"); 
					properties.put("host", address.ApplicationAndHost().get("hostIpAndPro"));
					properties.put("serviceUrl",address.ApplicationAndHost().get("applicationAddress"));
					properties.put("infoMsg", e.getMessage());
					properties.put("IsRestart", "true");
					tack.track("msgCustomer", "mqTrack", properties);
					tack.shutdown();
				}
			} catch (Exception e1) {
				log.info("埋点msgCustomer 发生异常");
			}
		}
    }
    
    
    
    
	protected void resendRabbitQ( String Exchange,String routeKey, String correlation, Object data) {

		rabbitTemplate.convertAndSend(routeKey, data, new MessagePostProcessor() {

			@Override
			public Message postProcessMessage(Message message) throws AmqpException {

                try {
                	 message.getMessageProperties().setCorrelationId(correlation.getBytes());
                    message.getMessageProperties().setContentType("json");
                   
                } catch (Exception e) {
                    throw new AmqpException(e);
                }
              
                return message;
                
            }
        }); 
    }

}
