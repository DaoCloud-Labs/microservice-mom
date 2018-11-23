package com.yonyou.cloud.mom.client.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yonyou.cloud.mom.client.consumer.ConsumerAspect;
import com.yonyou.cloud.mom.client.consumer.ReConsumerDefaultImpl;
import com.yonyou.cloud.mom.client.producer.MqSenderDefaultImpl;
import com.yonyou.cloud.track.Track;
/**
 * 
 * @author daniell
 *
 */
@Configuration
@ConditionalOnBean({MqSenderDefaultImpl.class,ConsumerAspect.class})
@ConditionalOnProperty(prefix = "track.isTacks", value = "true", havingValue = "true", matchIfMissing = true)
public class TrackConfig {
 
	@Value("${track.LoggingUrl:/opt/ops/track.log}")
	private String loggingUrl;
	
	@Value("${track.isTacks:false}")
	private Boolean isTacks; 
	
	@Autowired
	private MqSenderDefaultImpl mqSenderDefaultImpl;
	
	@Autowired
	private ConsumerAspect consumerAspect;
	
	@Autowired
	private ReConsumerDefaultImpl reConsumerDefaultImpl;
	
	@Bean
	public Track initTrack() throws IOException {
		System.out.println("埋点初始化成功");
		if(isTacks) {
			Track track=new Track(new Track.ConcurrentLoggingConsumer(loggingUrl));
			mqSenderDefaultImpl.setTack(track);
			consumerAspect.setTack(track);
			reConsumerDefaultImpl.setTack(track);
			return  track;
		}else {
			return null; 
		}
		
	}
  

}
