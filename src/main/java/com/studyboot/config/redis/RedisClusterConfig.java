package com.studyboot.config.redis;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;

import java.util.Set;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "spring.redis.cluster")
public class RedisClusterConfig {

//	@Value("${spring.redis.cluster.nodes}")
	private String nodes;
	
	private Integer timeout;
	
	private Integer redirects;
	
	public String getNodes() {
		return nodes;
	}

	public void setNodes(String nodes) {
		this.nodes = nodes;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public Integer getRedirects() {
		return redirects;
	}

	public void setRedirects(Integer redirects) {
		this.redirects = redirects;
	}

	public Set<HostAndPort> getRedisNods() {
		if (StringUtils.isNotBlank(nodes)) {
			String[] hosts = StringUtils.split(nodes, ",");
			Set<HostAndPort> nodeSet = Sets.newHashSet();
			for (String host : hosts) {
				String[] h_port = StringUtils.split(host, ":");
				if (h_port.length != 2) {
					continue;
				}
				nodeSet.add(new HostAndPort(h_port[0], Integer.valueOf(h_port[1])));
			}
			return nodeSet;
		}
		return null;
	}
}
