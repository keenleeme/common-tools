package com.latrell.test.service.impl;

import com.latrell.common.config.transaction.AtomicConcurrentTransactionalExecutor;
import com.latrell.test.domain.OperateLog;
import com.latrell.test.mapper.OperateLogMapper;
import com.latrell.test.service.OperateLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 系统操作日志记录 服务实现类
 * </p>
 *
 * @author liz
 * @since 2022-10-11
 */
@Service
public class OperateLogServiceImpl extends ServiceImpl<OperateLogMapper, OperateLog> implements OperateLogService {

    @Resource
    private AtomicConcurrentTransactionalExecutor atomicConcurrentTransactionalExecutor;

    @Override
    public void saveOperateLog(List<OperateLog> operateLogs) {
        int size = operateLogs.size();
        int middleSize = size / 2;
        if (size % 2 != 0) {
            middleSize++;
        }
        List<OperateLog> operateLogs1 = operateLogs.subList(0, middleSize);
        List<OperateLog> operateLogs2 = operateLogs.subList(middleSize, size);
        boolean execute = atomicConcurrentTransactionalExecutor.execute(5, () -> this.saveBatch(operateLogs1), () -> this.saveBatch(operateLogs2));
        System.out.println("执行结果：" + execute);
    }
}
