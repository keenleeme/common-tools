package com.latrell.test.util;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

/**
 * TODO
 *
 * @author liz
 * @date 2023/2/24-9:31
 */
@Slf4j
public class ObjectTest {

    public static void main(String[] args) throws InterruptedException {
        log.debug(ClassLayout.parseInstance(new Object()).toPrintable()); // 001 -- 无锁不可偏向
        // HotSpot虚拟机在启动后有个4s的延迟才会对每个新建的对象开启偏向锁模式
        Thread.sleep(4000);
        Object obj = new Object();

        new Thread(() -> {
            log.debug(Thread.currentThread().getName() + "开始执行。。。\n"
                    + ClassLayout.parseInstance(obj).toPrintable()); // 101 -- 无锁可偏向
            synchronized (obj) {
                log.debug(Thread.currentThread().getName() + "开始执行2。。。\n"
                        + ClassLayout.parseInstance(obj).toPrintable()); // 101 -- 偏向锁 mark word 存入当前线程ID
                obj.notifyAll();
                log.debug(Thread.currentThread().getName() + "获取锁执行。。。\n"
                        + ClassLayout.parseInstance(obj).toPrintable()); // 000 -- 偏向锁 mark word 存入当前线程ID
            }
            log.debug(Thread.currentThread().getName() + "释放锁执行。。。\n"
                    + ClassLayout.parseInstance(obj).toPrintable()); // 101 -- 无锁可偏向
        }, "Thread1").start();

        Thread.sleep(5000);
        log.debug(ClassLayout.parseInstance(obj).toPrintable());
    }

}
