package com.latrell.design.singleton;

/**
 * 懒汉模式-线程安全
 *
 * @author liz
 * @date 2023/3/12-11:46
 */
public class Singleton03 {

    private static Singleton03 instance;

    private Singleton03() {
    }

    /**
     * 懒汉模式 -- 线程安全 -- 比较重的加锁操作 锁住当前类的class对象
     */
    public static synchronized Singleton03 getInstance() {
        if (instance != null) {
            return instance;
        }
        return new Singleton03();
    }

}
