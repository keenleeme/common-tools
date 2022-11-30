package com.latrell.common.config.transaction;

import com.latrell.common.config.exception.CommonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 事务处理类
 *
 * @author liz
 * @date 2022/10/26-17:33
 */
@Component
public class TransactionalWorker {

    private static final Logger logger = LoggerFactory.getLogger(TransactionalWorker.class);

    @Transactional(rollbackFor = Exception.class)
    public void run(CyclicBarrier workCyclicBarrier, AtomicInteger successCounter, Runnable runnable) {
        boolean isSuccess = false;
        try {
            runnable.run();
            // 递减
            successCounter.decrementAndGet();
            isSuccess = true;
        } catch (Exception e) {
            logger.error("TransactionalWorker current thread executor error!", e);
            isSuccess = false;
            throw e;
        } finally {
            try {
                // 如果是数据库操作慢导致长时间阻塞，并不会被线程池中断(Interrupt)
                // 也就是会等到数据库操作完成之后，进入到这一步，然后直接报超时异常
                workCyclicBarrier.await();
            } catch (Exception e) {
                // 等待其他线程时超时
                logger.error("TransactionalWorker current thread execute CyclicBarrier.await!", e);
                if (isSuccess) {
                    // 要回滚计数，否则：假设全部线程都操作成功，但刚好超时，主线程shutdown线程池后，计数为0，会返回成功
                    successCounter.incrementAndGet();
                }
            }
        }
        if (successCounter.get() != 0) {
            logger.error("TransactionalWorker other thread execute error, create CommonException to rollback!");
            throw new CommonException("TransactionalWorker other thread execute error, create CommonException to rollback!");
        }
    }

}
