package com.latrell.common.config.spring.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 事件发布者
 *
 * @author liz
 * @date 2022/10/10-19:16
 */
@Component
public class EventPublisher implements ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventPublisher.class);

    private static ApplicationContext applicationContext;

    public EventPublisher() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        EventPublisher.applicationContext = applicationContext;
    }

    public static void publishEvent(Object event) {
        LOGGER.info("正在发布自定义事件……");
        EventPublisher.applicationContext.publishEvent(event);
        LOGGER.info("自定义事件发布完成：{}", event);
    }
}
