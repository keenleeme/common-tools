package com.latrell.jni;

import java.util.Vector;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantLock;

/**
 * test class
 *
 * @author liz
 * @date 2023/3/19-13:28
 */
public class HelloWorld {

    static {
        // 加载HelloWorld动态库
        System.loadLibrary("HelloWorld");
    }

    public native void sayHello();

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("123");
        });
        thread.interrupt();
        thread.isInterrupted();
        ReentrantLock reentrantLock = new ReentrantLock();
        Vector<Object> objects = new Vector<>();
    }

}
