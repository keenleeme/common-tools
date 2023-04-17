package com.latrell.design.proxy;

import java.lang.reflect.Proxy;

/**
 * JDK动态代理
 *
 * @author liz
 * @date 2023/3/12-12:13
 */
public class JDKProxy {

    /**
     * JDK 动态代理
     * @param interfaces 接口
     * @return 代理对象
     */
    public static Object getProxy(Class<?>[] interfaces) {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), interfaces, (proxy, method, args) -> method.invoke(proxy, args));
    }

}
