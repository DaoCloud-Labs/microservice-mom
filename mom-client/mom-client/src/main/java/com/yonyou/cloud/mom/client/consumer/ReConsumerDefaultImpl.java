package com.yonyou.cloud.mom.client.consumer;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.yonyou.cloud.mom.core.dto.ConsumerDto;
import com.yonyou.cloud.mom.core.store.ConsumerMsgStore;
import com.yonyou.cloud.mom.core.store.StoreStatusEnum;

import net.sf.json.JSONObject;

@Service
public class ReConsumerDefaultImpl  implements ReConsumerDefault {
	Logger log= LoggerFactory.getLogger(ReConsumerDefaultImpl.class);
	
	@Autowired
	private ConsumerMsgStore msgStore ;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Override
	public void reConsumer() throws  Exception {
		List<ConsumerDto> list=msgStore.selectReConsumerList(StoreStatusEnum.CONSUMER_FAILD.getValue());
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
			Class c =Class.forName(msgEntity.getBizClassName()); //创建一个类
			 JSONObject obj = new JSONObject().fromObject(msgEntity.getMsgContent());
			 Object ojbClass = JSONObject.toBean(obj,c);//把json转化成指定的对象
			 
			 resendRabbitQ( msgEntity.getRouterKey(),msgEntity.getMsgKey(),  ojbClass); 
			 
			 
//			 Class<?> ConsumerClass =Class.forName(msgEntity.getConsumerClassName()); 
//			Object objListen= getConsumerListen(ConsumerClass);
//			AbstractConsumerListener consumerListener=(AbstractConsumerListener) objListen;
//			consumerListener.handleMessage(ojbClass); 
			 //更新状态
//			 msgStore.updateMsgSuccess(msgEntity.getMsgKey());
			 System.out.println("执行完毕");
			 
		} catch (Exception e) {
			throw new AmqpException(e);
		}
    }
    
    
    
    
	protected void resendRabbitQ(String routeKey, String correlation, Object data) {

		rabbitTemplate.convertAndSend(routeKey, data, new MessagePostProcessor() {

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
