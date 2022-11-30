package com.latrell.common.config.rocketmq;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * API 日志rocketMQ消费者
 *
 * @author liz
 * @date 2022/10/13-15:59
 */
@Component
public class ApiLogConsumer {

    private static final Logger logger = LoggerFactory.getLogger(ApiLogConsumer.class);

    @Resource
    private JmsConfig jmsConfig;

    public ApiLogConsumer() {
    }

    @PostConstruct
    public void consumerStart() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(jmsConfig.getApiLogConsumerGroup());
        // 绑定 NameServer 服务器
        consumer.setNamesrvAddr(jmsConfig.getNameServerUrl());
        // 消费模式：一个新的订阅组第一次启动从队列的最后位置开始消费。后续再启动从上次消费的进度继续消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 订阅主题和标签 topic 主题 '*' 表示主题下的所有标签
        consumer.subscribe(jmsConfig.getApiLogTopic(), "*");
        // 消息模式：广播消费
//        consumer.setMessageModel(MessageModel.CLUSTERING);
        // 注册消费者的监听器，并在监听器中消费消息，并返回消息消费状态
        consumer.registerMessageListener((MessageListenerConcurrently) (messages, context) -> {
            // messages只会收集同一种topic、同一个tag并且key相同的消息
            // 会把不同的主题的消息放进不同的队列中
            if (CollUtil.isEmpty(messages)) {
                logger.info("接收到的消息为空，不需要处理");
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }

            try {
                for (MessageExt message : messages) {
                    String body = StrUtil.str(message.getBody(), "UTF-8");
                    logger.info("API日志Consumer-消费消息-主题topic：{}，消息内容：{}", message.getTopic(), body);
                }
            } catch (Exception e) {
                logger.warn("Consumer-获取消息异常：{}", e);
                // 获取消息失败之后，后续会触发重试机制
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        // 消费者启动
        consumer.start();
        logger.info("API 日志消费者 启动成功...");
    }
}
