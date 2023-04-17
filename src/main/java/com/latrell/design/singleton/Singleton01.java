package com.latrell.design.singleton;

/**
 * 单例模式-饿汉模式
 *
 * @author liz
 * @date 2023/3/12-11:42
 */
public class Singleton01 {

    private static final Singleton01 instance = new Singleton01();

    private Singleton01() {
    }

    /**
     * 饿汉模式-线程安全
     */
    public static Singleton01 getInstance() {
        return instance;
    }

}
