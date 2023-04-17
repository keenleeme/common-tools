package com.latrell.design.singleton;

/**
 * 懒汉模式 -- 线程安全
 *
 * @author liz
 * @date 2023/3/12-11:49
 */
public class Singleton04 {

    private static volatile Singleton04 instance;

    private Singleton04() {
    }

    /**
     * 懒汉模式 -- 线程安全 -- 双重检测
     */
    public static Singleton04 getInstance() {
        if (instance != null) {
            return instance;
        }
        synchronized (Singleton04.class) {
            if (instance == null) {
                return new Singleton04();
            }
            return instance;
        }
    }

}
