package com.latrell.common.config.spring.aspect.limit;

import cn.hutool.core.map.MapUtil;
import com.latrell.common.config.exception.CommonException;
import com.latrell.common.domain.response.ReturnCodeEnum;
import com.latrell.common.util.RequestUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * AOP 流量限制处理
 * 用于并发量较高的接口，限制接口请求
 * 缺点：当最后 1s钟，接收了大量的请求，系统依然是撑不住的
 *
 * @author liz
 * @date 2022/10/24-17:18
 */
@Aspect
@Component
public class FlowLimitAspect {

    private static final Logger logger = LoggerFactory.getLogger(FlowLimitAspect.class);

    private static final String LAST_ACCESS_TIME = "lastAccessTime";
    private static final String API_ACCESS_ACCOUNT = "apiAccessAccount";
    private static final Map<String, Map<String, Object>> API_ACCESS_INFO = new ConcurrentHashMap<>();

    @Value("${flowLimit.maxCount}")
    private Integer maxCount;

    @Pointcut("@annotation(com.latrell.common.config.spring.aspect.limit.FlowLimit)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object flowLimit(ProceedingJoinPoint pjp) throws Throwable {
        // 当前时间戳
        long currentTimeMillis = System.currentTimeMillis();
        HttpServletRequest request = RequestUtil.getRequest();
        // 请求接口 API
        String requestUri = request.getRequestURI();
        Map<String, Object> apiAccess = API_ACCESS_INFO.get(requestUri);
        // 如果当前API接口对应的信息为不为空，进行逻辑处理
        if (MapUtil.isNotEmpty(apiAccess)) {
            long lastAccessTime = (long) apiAccess.get(LAST_ACCESS_TIME);
            long passedMinute = (currentTimeMillis - lastAccessTime) / (60 * 1000);
            // 如果距离上次请求时间 未超过1分钟 则对请求数进行判断
            if (passedMinute < 1) {
                Integer lastCount = (Integer) apiAccess.get(API_ACCESS_ACCOUNT);
                if (lastCount < maxCount) {
                    apiAccess.put(API_ACCESS_ACCOUNT, lastCount + 1);
                } else {
                    throw new CommonException(ReturnCodeEnum.EXCESSIVE_API_REQUEST_IN_ONE_MINUTE.getValue(), "1分钟内接口请求次数过量");
                }
            } else {
                // 如果距离上次请求时间 超过1分钟，重置时间和请求次数
                apiAccess.put(LAST_ACCESS_TIME, currentTimeMillis);
                apiAccess.put(API_ACCESS_ACCOUNT, 1);
            }
        } else {
            // 如果当前API接口，第一次接收到请求，则初始化
            apiAccess = new ConcurrentHashMap<>();
            apiAccess.put(LAST_ACCESS_TIME, currentTimeMillis);
            apiAccess.put(API_ACCESS_ACCOUNT, 1);
        }
        API_ACCESS_INFO.put(requestUri, apiAccess);
        return pjp.proceed();
    }

}
