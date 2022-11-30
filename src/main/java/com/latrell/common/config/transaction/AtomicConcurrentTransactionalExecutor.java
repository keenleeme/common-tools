package com.latrell.common.config.transaction;

import com.latrell.common.util.ThreadPoolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * 原子多线程事务执行器
 *
 * @author liz
 * @date 2022/10/26-17:30
 */
@Component
public class AtomicConcurrentTransactionalExecutor {

    private static final Logger logger = LoggerFactory.getLogger(AtomicConcurrentTransactionalExecutor.class);

    @Autowired
    private TransactionalWorker transactionalWorker;

    public boolean execute(int threadWaitTerminationTimeout, Runnable... runnables) {
        int threadSizes = runnables.length;
        CyclicBarrier workerCyclicBarrier = new CyclicBarrier(threadSizes);
        AtomicInteger successCounter = new AtomicInteger(threadSizes);
        ThreadPoolExecutor executor = ThreadPoolUtil.newFixedThreadPoolExecutor(threadSizes);
        for (Runnable runnable : runnables) {
            executor.submit(() -> {
               try {
                   transactionalWorker.run(workerCyclicBarrier, successCounter, runnable);
               } catch (Exception e) {
                   logger.error("TransactionalWorker current thread execute error before runnable.run!", e);
               }
            });
        }
        ThreadPoolUtil.shutdown(executor, threadWaitTerminationTimeout, TimeUnit.SECONDS);
        return successCounter.get() == 0;
    }

    public boolean execute(int threadWaitTerminationTimeout, int threadPoolSize, Runnable runnable) {
        Runnable[] runnables = IntStream.range(0, threadPoolSize).mapToObj(i -> runnable).toArray(Runnable[]::new);
        return execute(threadWaitTerminationTimeout, runnables);
    }

}
