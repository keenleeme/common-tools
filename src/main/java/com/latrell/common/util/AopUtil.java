package com.latrell.common.util;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.lang.reflect.Method;

/**
 * Aop 处理工具类
 *
 * @author liz
 * @date 2022/10/11-21:23
 */
public class AopUtil {

    private static final Logger logger = LoggerFactory.getLogger(AopUtil.class);

    /**
     * 根据AOP切点信息获取当前方法信息
     *
     * @param point 切点
     * @return method 方法
     */
    public static Method getTargetMethod(ProceedingJoinPoint point) {
        Method method = null;
        try {
            MethodSignature signature = (MethodSignature)point.getSignature();
            method = point.getTarget().getClass().getDeclaredMethod(signature.getName(), signature.getParameterTypes());
        } catch (NoSuchMethodException | SecurityException e) {
            logger.warn("获取目标方法异常: {}", e);
        }
        Assert.isTrue(method != null, "获取目标方法失败");
        return method;
    }

    /**
     * 根据注解内的字段和参数获取参数值
     *
     * @param field 注解内的字段
     * @param args 参数
     * @return 属性值
     */
    public static String getFieldValue(String field, Object[] args) {
        String fieldValue = null;
        for (Object arg : args) {
            try {
                if (StrUtil.isBlank(fieldValue)) {
                    fieldValue = BeanUtils.getProperty(arg, field);
                } else {
                    break;
                }
            } catch (Exception e) {
                if (args.length == 1) {
                    return args[0].toString();
                }
            }
        }
        return fieldValue;
    }

}
