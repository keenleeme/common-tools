package com.latrell.common.util;

import com.latrell.common.domain.constant.CommonConstants;
import com.latrell.test.domain.SysUser;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 请求相关工具
 *
 * @author liz
 * @date 2022/10/11-16:26
 */
public class RequestUtil {

    public RequestUtil() {
    }

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    public static HttpSession getSession(HttpServletRequest request) {
        return request.getSession();
    }

    public static String getSessionUrl(HttpServletRequest request) {
        return request.getScheme() +
                "://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath();
    }

    public static String getSessionUserId() {
        return (String) getSession().getAttribute(CommonConstants.SESSION_USER_ID_KEY);
    }

    public static String getSessionUserId(HttpServletRequest request) {
        return (String) getSession(request).getAttribute(CommonConstants.SESSION_USER_ID_KEY);
    }

    public static SysUser getSessionUser() {
        return (SysUser) getSession().getAttribute(CommonConstants.SESSION_USER_KEY);
    }

    public static String getSessionUserName() {
        return (String) getSession().getAttribute(CommonConstants.SESSION_USER_NAME_KEY);
    }

    public static String getSessionUserRealName() {
        return (String) getSession().getAttribute(CommonConstants.SESSION_USER_REAL_NAME_KEY);
    }

    public static String getRequestUri() {
        return getRequest().getRequestURI();
    }

}
