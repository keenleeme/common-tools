package com.latrell.common.config.spring.aspect.log;

import java.lang.annotation.*;

/**
 * API 接口日志注解
 *
 * @author liz
 * @date 2022/10/11-14:31
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface ApiLog {

    /**
     * 接口名称描述
     */
    String value() default "";

    /**
     * 是否输出日志，默认输出
     */
    boolean loggable() default true;

    /**
     * 接口操作模块
     */
    String module() default "";

    /**
     * 接口操作类型
     */
    String opType() default "";

    /**
     * 接口操作详细信息
     */
    String message() default "";

}
