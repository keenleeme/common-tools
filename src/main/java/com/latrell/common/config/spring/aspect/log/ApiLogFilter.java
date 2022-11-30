package com.latrell.common.config.spring.aspect.log;

import com.alibaba.fastjson.JSONObject;
import com.latrell.common.domain.constant.CommonConstants;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.ServletRequestPathUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

/**
 * 接口日志统一过滤处理器
 *
 * @author liz
 * @date 2022/10/12-17:05
 */
@Order(1)
@Component
public class ApiLogFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(ApiLogFilter.class);

    /**
     * request 属性名称。在request中存储此接口中的@ApiLog注解，用于在请求信息暂存、前后取值
     * 规则：类名 + 自定义后缀，避免重名
     */
    private static final String API_LOG_REQUEST_ATTRIBUTE_NAME = ApiLogFilter.class.getName() + ".API_LOG";

    /**
     * 请求处理器映射关系，可以根据请求信息获取对应的接口
     */
    private final RequestMappingHandlerMapping requestMappingHandlerMapping;

    /**
     * 日志记录是否被禁用，已禁用的情况下直接放行，不再记录日志
     */
    @Setter
    private static boolean disabled = false;

    public ApiLogFilter(RequestMappingHandlerMapping requestMappingHandlerMapping) {
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
    }

    @Override
    protected boolean shouldNotFilter(@NotNull HttpServletRequest request) throws ServletException {
        if (disabled) {
            // 如果禁用了日志，则直接放行
            return true;
        }
        // 获取当前请方法的ApiLog注解
        ApiLog apiLog = this.getRequestApiLogAnnotation(request);
        // 存入 request 请求中
        request.setAttribute(API_LOG_REQUEST_ATTRIBUTE_NAME, apiLog);
        boolean logApi = true;
        if (apiLog != null) {
            logApi = apiLog.loggable();
        }
        return !logApi;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpRequest, HttpServletResponse httpResponse, FilterChain filterChain) throws ServletException, IOException {
        httpRequest = new RequestLoggingWrapper(httpRequest, 1024);
        parseApiLogRequestParams(httpRequest);
        // 调用链放行，执行业务操作
        filterChain.doFilter(httpRequest, httpResponse);
    }

    public ApiLog getRequestApiLogAnnotation(HttpServletRequest request) {
        ApiLog apiLogAnnotation = null;
        try {
            // 获取处理器，提前解析一下
            ServletRequestPathUtils.parseAndCache(request);
            // 获取当前HTTP请求对应的处理器
            HandlerExecutionChain handler = requestMappingHandlerMapping.getHandler(request);
            if (handler != null) {
                // 获取处理器的处理方法
                HandlerMethod handlerMethod = (HandlerMethod) handler.getHandler();
                // 获取处理方法对象的 ApiLog 注解
                apiLogAnnotation = handlerMethod.getMethodAnnotation(ApiLog.class);
            }
        } catch (Exception e) {
            logger.warn("获取请求处理器异常：{}", e);
        }
        return apiLogAnnotation;
    }

    private void parseApiLogRequestParams(HttpServletRequest httpRequest) {
        // 请求方法
        String method = httpRequest.getMethod();
        // 接口请求参数
        String requestParams = null;
        if (method.equals("GET")) {
            JSONObject jsonObject = new JSONObject();
            Map<String, String[]> parameterMap = httpRequest.getParameterMap();
            for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                jsonObject.put(entry.getKey(), entry.getValue());
            }
            requestParams = jsonObject.isEmpty() ? null : jsonObject.toJSONString();
        }
        /*else if (method.equals("POST")) {
            RequestLoggingWrapper loggingWrapper = new RequestLoggingWrapper(httpRequest, 1024);
            try {
                StringBuilder stringBuilder = new StringBuilder();
                BufferedReader reader = loggingWrapper.getReader();
                char[] charBuffer = new char[128];
                int bytesRead;
                while ((bytesRead = reader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
                requestParams = stringBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
        ApiLogContext.put(CommonConstants.REQUEST_PARAMS, requestParams);
    }

}
