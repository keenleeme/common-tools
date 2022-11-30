package com.latrell.common.config.spring.aspect.log;

import java.util.HashMap;
import java.util.Map;

/**
 * API 日志上下文
 *
 * @author liz
 * @date 2022/10/11-22:11
 */
public class ApiLogContext {

    private static final ThreadLocal<Map<String, Object>> LOCAL = new ThreadLocal<>();

    public static ThreadLocal<Map<String, Object>> getLocal() {
        return LOCAL;
    }

    public static void put(String key, Object value) {
        if (LOCAL.get() != null) {
            LOCAL.get().put(key, value);
        } else {
            Map<String, Object> localMap = new HashMap<>();
            localMap.put(key, value);
            LOCAL.set(localMap);
        }
    }

    public static Object get(String key) {
        if (LOCAL.get() == null || !LOCAL.get().containsKey(key)) {
            return null;
        }
        return LOCAL.get().get(key);
    }

}
