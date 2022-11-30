package com.latrell.common.config.spring.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 自定义事件广播器
 *
 * @author liz
 * @date 2022/10/11-10:31
 */
@Component
public class CustomEventCaster {

    private static final Logger logger = LoggerFactory.getLogger(CustomEventCaster.class);

    @EventListener(ApplicationStartedEvent.class)
    public void applicationStarted() {
        logger.info("应用启动成功后，发送通知事件");
    }

}
