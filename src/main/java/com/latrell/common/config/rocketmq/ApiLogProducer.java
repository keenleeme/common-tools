package com.latrell.common.config.rocketmq;

import com.alibaba.fastjson.JSONObject;
import com.latrell.test.domain.OperateLog;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * API日志生产者类
 *
 * @author liz
 * @date 2022/10/13-15:52
 */
@Component
public class ApiLogProducer {

    private static final Logger logger = LoggerFactory.getLogger(ApiLogProducer.class);

    @Resource
    private JmsConfig jmsConfig;

    private DefaultMQProducer producer;

    public ApiLogProducer() {
    }

    @PostConstruct
    public void producerStart() {
        /**
         * 默认一个消费者组只会对应一个主题
         * 默认消息队列的数量 defaultTopicQueueNums=4
         * 多个消息队列的目的是提高并发量
         *
         * RocketMQ 在主题层面是无序的，在队列层面(单个队列内部)是有序的；
         * 同一个消息队列收到的消息是有序的，不同消息队列收到的消息是无序的。
         * 顺序消费：普通顺序，严格顺序
         * 普通顺序：同一个消息队列收到的消息是有序的，不同消息队列收到的消息是无序的。普通顺序消息在 broker 重启情况下不会保证消息的有序性（短暂时间）
         * 严格顺序：消费者收到的所有消息都是有序的，即使在异常情况下收到的消息都是有序的。
         * 注：严格顺序固然好，但要付出的代价的太高，一旦有一个broker节点不可用，则整个集群都不可用。
         * 一般而言，MQ 都可以容忍短暂的乱序，所以推荐使用普通顺序。
         *
         *
         */
        producer = new DefaultMQProducer(jmsConfig.getApiLogProducerGroup());
        producer.setVipChannelEnabled(false);
        producer.setNamesrvAddr(jmsConfig.getNameServerUrl());
        start();
    }

    public void start() {
        try {
            this.producer.start();
            logger.info("API 日志生产者 启动成功...");
        } catch (MQClientException e) {
            logger.warn("API 日志启动生产者异常：{}", e);
        }
    }

    public DefaultMQProducer getProducer() {
        return producer;
    }

    public void sendMsg(OperateLog operateLog) {
        Message message = new Message(jmsConfig.getApiLogTopic(), JSONObject.toJSONString(operateLog).getBytes());
        try {
            SendResult sendResult = getProducer().send(message);
            logger.info("API日志Producer-创建消息-主题topic：{}，返回结果：{}", jmsConfig.getApiLogTopic(), JSONObject.toJSONString(sendResult));
        } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
            logger.warn("生产者发送消息异常：{}", e);
        }
    }

    public void shutdown() {
        this.producer.shutdown();
    }

}
