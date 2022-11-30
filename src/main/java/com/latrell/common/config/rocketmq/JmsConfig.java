package com.latrell.common.config.rocketmq;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * RocketMQ 全局配置信息
 *
 * @author liz
 * @date 2022/10/13-15:40
 */
@Getter
@Component
public class JmsConfig {

    /**
     * nameServer 地址
     */
    @Value(value = "${rocketmq.name-server}")
    public String nameServerUrl;

    /**
     * 日志信息topic
     */
    @Value(value = "${rocketmq.apiLog.topic}")
    public String apiLogTopic;

    /**
     * 日志生产者组
     */
    @Value(value = "${rocketmq.producer.groups}")
    public String apiLogProducerGroup;

    /**
     * 日志消费者组
     */
    @Value(value = "${rocketmq.consumer.groups}")
    public String apiLogConsumerGroup;

}
