package com.yonyou.cloud.mom.client.config;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.Query;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
/**
 * 
 * @author daniell
 *
 */
@Component
public class AddressConfig {
	protected final static Logger logger = LoggerFactory.getLogger(AddressConfig.class);

	@Autowired
	private Environment env;

	public String applicationAddress() throws UnknownHostException, MalformedObjectNameException {

		String serviceName = env.getProperty("spring.application.name");
		String eureka = env.getProperty("eureka.client.serviceUrl.defaultZone");

		MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
		Set<ObjectName> objectNames = beanServer.queryNames(new ObjectName("*:type=Connector,*"),
				Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));

		String host = InetAddress.getLocalHost().getHostAddress();

		String port = objectNames.iterator().next().getKeyProperty("port");

		StringBuffer adr = new StringBuffer();
		adr.append("http://");
		if (StringUtils.isNotBlank(eureka)) {
			logger.info("这是一个微服务，并且注入到eureka");
			adr.append(serviceName);
		} else {
			logger.info("这个不是注入到eureka的微服务");
			adr.append(host);
		}
		adr.append(":");
		adr.append(port);
		logger.info(adr.toString());
		return adr.toString();

	}

	public String hostIpAndPro() throws UnknownHostException, MalformedObjectNameException {
		MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
		Set<ObjectName> objectNames = beanServer.queryNames(new ObjectName("*:type=Connector,*"),
				Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));
		String host = InetAddress.getLocalHost().getHostAddress();
		String port = objectNames.iterator().next().getKeyProperty("port");
		StringBuffer adr = new StringBuffer();
		adr.append("http://");
		adr.append(host);
		adr.append(":");
		adr.append(port);
		logger.info(adr.toString());
		return adr.toString();
	}

	public Map<String, String> applicationAndHost() throws UnknownHostException, MalformedObjectNameException {
		Map<String, String> map = new HashMap<>(3);
		String serviceName = env.getProperty("spring.application.name");
		String eureka = env.getProperty("eureka.client.serviceUrl.defaultZone");

		MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
		Set<ObjectName> objectNames = beanServer.queryNames(new ObjectName("*:type=Connector,*"),
				Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));

		String host = InetAddress.getLocalHost().getHostAddress();

		String port = objectNames.iterator().next().getKeyProperty("port");

		StringBuffer adr = new StringBuffer();
		adr.append("http://");
		if (StringUtils.isNotBlank(eureka)) {
			logger.info("这是一个微服务，并且注入到eureka");
			adr.append(serviceName);
		} else {
			logger.info("这个不是注入到eureka的微服务");
			adr.append(host);
		}
		adr.append(":");
		adr.append(port);
		logger.info(adr.toString());

		map.put("applicationAddress", adr.toString());
		map.put("hostIpAndPro", "http://" + host + ":" + port);
		return map;
	}
}
