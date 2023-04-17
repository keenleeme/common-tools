package com.latrell.design.singleton;

/**
 * 线程安全--线程安全
 *
 * @author liz
 * @date 2023/3/12-11:52
 */
public class Singleton05 {

    public static class Singleton05Holder {
        private static final Singleton05 instance = new Singleton05();
    }

    private Singleton05() {
    }

    /**
     * 懒汉模式 -- 线程安全 -- 静态内部类
     */
    public static Singleton05 getInstance() {
        return Singleton05Holder.instance;
    }

}
