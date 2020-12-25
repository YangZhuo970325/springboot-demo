package com.yangzhuo.zookeeper.curator.config;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZkConfig {
    @Value("${zk.url}")
    private String zkUrl;

    @Value("${zk.sessionTimeout}")
    private int sessionTimeout;

    @Value("${zk.connectTimeout}")
    private int connectTimeout;

    @Value("${zk.namespace}")
    private String namespace;

    @Bean
    public CuratorFramework curatorFramework() {
        //baseSleepTimeMs : 重连间隔时间   maxRetries : 最大尝试次数
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString(zkUrl)
                .sessionTimeoutMs(sessionTimeout).connectionTimeoutMs(connectTimeout)
                .retryPolicy(retryPolicy).namespace(namespace).build();
        client.start();
        return client;
    }
}
