package com.latrell.design.singleton;

/**
 * 懒汉模式-线程不安全
 *
 * @author liz
 * @date 2023/3/12-11:44
 */
public class Singleton02 {

    private static Singleton02 instance;

    private Singleton02() {
    }

    /**
     * 懒汉模式-线程不安全
     */
    public static Singleton02 getInstance() {
        if (instance != null) {
            return instance;
        }
        return new Singleton02();
    }

}
