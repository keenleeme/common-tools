package com.latrell.design.singleton;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 懒汉模式--线程安全
 *
 * @author liz
 * @date 2023/3/12-11:55
 */
public class Singleton06 {

    private static final AtomicReference<Singleton06> ATOMIC_REFERENCE = new AtomicReference<>();

    private Singleton06() {
    }

    /**
     * 懒汉模式 -- CAS
     */
    public static Singleton06 getInstance() {
        for (;;) {
            Singleton06 instance = ATOMIC_REFERENCE.get();
            if (instance != null) {
                return instance;
            }
            ATOMIC_REFERENCE.compareAndSet(null, new Singleton06());
            return ATOMIC_REFERENCE.get();
        }

    }

}
