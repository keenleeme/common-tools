package com.latrell.common.config.spring.aspect.log;

import cn.hutool.core.net.NetUtil;
import com.alibaba.fastjson.JSONObject;
import com.latrell.common.config.rocketmq.ApiLogProducer;
import com.latrell.common.domain.constant.CommonConstants;
import com.latrell.common.util.AopUtil;
import com.latrell.common.util.RequestUtil;
import com.latrell.common.util.ThreadPoolUtil;
import com.latrell.test.domain.OperateLog;
import com.latrell.test.mapper.OperateLogMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * API 接口日志切面处理
 *
 * @author liz
 * @date 2022/10/11-16:03
 */
@Aspect
@Component
public class ApiLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(ApiLogAspect.class);
    private final ThreadPoolExecutor executor = ThreadPoolUtil.newFixedThreadPoolExecutor(10);

    @Resource
    private OperateLogMapper operateLogMapper;
    @Resource
    private ApiLogProducer apiLogProducer;

    @Pointcut("@annotation(com.latrell.common.config.spring.aspect.log.ApiLog)")
    public void apiLogPointcut() {
    }

    @Around(value = "apiLogPointcut()")
    public Object process(ProceedingJoinPoint joinPoint) {
        long startMillis = System.currentTimeMillis();
        Object result = null;
        Method method = AopUtil.getTargetMethod(joinPoint);
        ApiLog apiLogAnnotation = method.getAnnotation(ApiLog.class);
        // 是否输出日志
        boolean loggable = apiLogAnnotation.loggable();
        OperateLog operateLog = operateLogWrapper(apiLogAnnotation);
        try {
            result = joinPoint.proceed();
            operateLog.setResponseContent(JSONObject.toJSONString(result));
            if (ApiLogContext.getLocal().get() != null) {
                Map<String, Object> map = ApiLogContext.getLocal().get();
                operateLog.setOpResult((Integer) map.get(CommonConstants.OP_RESULT));
                operateLog.setDetailMessage((String) map.get(CommonConstants.DETAIL_MESSAGE));
            }
            logger.info("操作IP地址：{}，\n接口Uri: {}，\n请求参数：{}，\n响应内容：{}, \n接口请求耗时：{} s", operateLog.getOpIpAddress(), operateLog.getApiUri(),
                    operateLog.getRequestParams(), operateLog.getResponseContent(), (System.currentTimeMillis() - startMillis) / 1000.0);
        } catch (Exception e) {
            operateLog.setOpResult(0);
            operateLog.setDetailMessage(e.getMessage());
            logger.warn("接口操作异常：{}，\n操作IP地址：{}，\n接口Uri: {}，\n请求参数：{}，\n接口请求耗时：{} s", e, operateLog.getOpIpAddress(), operateLog.getApiUri(),
                    operateLog.getRequestParams(), (System.currentTimeMillis() - startMillis) / 1000.0);
        } catch (Throwable throwable) {
            operateLog.setOpResult(0);
            throwable.printStackTrace();
        } finally {
            // 判断接口是否需要输出日志
            if (loggable) {
                executor.submit(() -> {
                    // 1、操作日志入库
                    operateLogMapper.insert(operateLog);
                    // 2、操作日志推送到MQ
                    apiLogProducer.sendMsg(operateLog);
                });
            }
        }
        return result;
    }

    /**
     * 封装操作日志实例
     */
    public OperateLog operateLogWrapper(ApiLog apiLogAnnotation) {
        // 操作时间
        LocalDateTime opTime = LocalDateTime.now();
        HttpServletRequest request = RequestUtil.getRequest();
        // todo 暂时没有操作用户 默认为 admin
        String opUserName = "admin";
        // api_uri 请求接口
        String apiUri = request.getRequestURI();
        // 接口描述信息
        String value = apiLogAnnotation.value();
        // 接口操作模块
        String module = apiLogAnnotation.module();
        // 接口操作类型
        String opType = apiLogAnnotation.opType();
        // 接口详细描述信息
        String message = apiLogAnnotation.message();
        // 操作IP地址
        String opIpAddress = NetUtil.getLocalhost().getHostAddress();
        String requestParams = null;
        if (ApiLogContext.getLocal().get() != null) {
            requestParams = (String) ApiLogContext.getLocal().get().get(CommonConstants.REQUEST_PARAMS);
        }
        return new OperateLog(opUserName, apiUri, value, module, opType, requestParams, message, opIpAddress, opTime);
    }

}
