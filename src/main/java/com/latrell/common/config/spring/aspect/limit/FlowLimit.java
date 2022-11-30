package com.latrell.common.config.spring.aspect.limit;

import java.lang.annotation.*;

/**
 * 限流注解
 *
 * @author liz
 * @date 2022/10/24-17:14
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FlowLimit {
}
