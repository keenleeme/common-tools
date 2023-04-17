package com.latrell.design.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

/**
 * CGLIB动态代理
 *
 * @author liz
 * @date 2023/3/12-12:21
 */
public class CGLIBProxy {

    /**
     * CgLIB 动态代理 既支持代理接口，也支持代理类。
     *
     * @param interfaces 接口
     * @param superClass 类
     * @return 代理对象
     */
    public static Object getProxy(Class<?>[] interfaces, Class<?> superClass) {
        Enhancer enhancer = new Enhancer();
        enhancer.setInterfaces(interfaces);
        enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> methodProxy.invoke(o, objects));
        enhancer.setSuperclass(superClass);
        return enhancer.create();
    }

}
