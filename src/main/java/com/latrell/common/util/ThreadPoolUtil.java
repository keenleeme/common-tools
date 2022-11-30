package com.latrell.common.util;

import ch.qos.logback.core.util.TimeUtil;
import cn.hutool.core.thread.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池工具类
 *
 * @author liz
 * @date 2022/10/11-21:40
 */
public class ThreadPoolUtil {

    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolUtil.class);

    private ThreadPoolUtil() {
    }

    /**
     * 创建固定线程数的线程池
     *
     * @param coreSize 核心线程数
     * @return executor 执行器
     */
    public static ThreadPoolExecutor newFixedThreadPoolExecutor(int coreSize) {
        return new ThreadPoolExecutor(coreSize, coreSize, 0L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100),
                new ThreadFactoryBuilder().setNamePrefix("fixed-thread-").build(), new ThreadPoolExecutor.CallerRunsPolicy());
    }

    /**
     * 关闭线程池
     *
     * @param pool 线程池
     * @param awaitTerminationTimeout 等待终止超时
     * @param timeUnit 时间单位
     * @return 关闭线程池成功/失败
     */
    public static boolean shutdown(ExecutorService pool, int awaitTerminationTimeout, TimeUnit timeUnit) {
        try {
            pool.shutdown();
            boolean done = false;
            try {
                done = awaitTerminationTimeout > 0 && pool.awaitTermination(awaitTerminationTimeout, timeUnit);
            } catch (InterruptedException e) {
                logger.error("thread pool awitTermination error!", e);
            }
            if (!done) {
                pool.shutdownNow();
                if (awaitTerminationTimeout > 0) {
                    pool.awaitTermination(awaitTerminationTimeout, timeUnit);
                }
            }
        } catch (Exception e) {
            logger.error("thread pool shutdown error!", e);
            return false;
        }
        return true;
    }

}
