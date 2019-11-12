package com.studyboot.config.redis;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Set;

@Configuration
@AutoConfigureAfter(RedisClusterConfig.class)
@EnableAutoConfiguration
public class RedisConfig {


    @Autowired
    private RedisClusterConfig redisClusterConfig;

    @Bean
    @ConfigurationProperties(prefix = "spring.redis.pool")
    public JedisPoolConfig getRedisConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        return config;
    }


    /**
     * 配置 Redis Cluster 信息
     */
    @Bean
    public RedisClusterConfiguration getJedisCluster() {
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        redisClusterConfiguration.setMaxRedirects(getRedisConfig().getMaxTotal());
        List<RedisNode> nodeList = Lists.newArrayList();
        Set<HostAndPort> nodes = redisClusterConfig.getRedisNods();
        for (HostAndPort node : nodes) {
            nodeList.add(new RedisNode(node.getHost(), node.getPort()));
        }
        redisClusterConfiguration.setClusterNodes(nodeList);
        return redisClusterConfiguration;
    }

    /**
     * 配置 Redis 连接工厂
     */
    @Bean(name = "jedisConnectionFactory")
    public JedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(getJedisCluster(), getRedisConfig());
        //new JedisConnectionFactory(new RedisClusterConfiguration(Arrays.asList(redisClusterConfig.getNodes().split(","))));
        return jedisConnectionFactory;
    }


}
