package com.yonyou.cloud.mom.client.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.yonyou.cloud.track.Track;

@Configuration
public class TrackConfig {
 
	@Value("${track.LoggingUrl:/opt/ops/track.log}")
	private String LoggingUrl;
	
	@Value("${track.isTacks:false}")
	private Boolean isTacks; 
	
	@Bean
	public Track initTrack() throws IOException {
		System.out.println("埋点初始化成功");
		if(isTacks) {
			return  new Track(new Track.ConcurrentLoggingConsumer(LoggingUrl));
		}else {
			return null; 
		}
		
	}
  

}
