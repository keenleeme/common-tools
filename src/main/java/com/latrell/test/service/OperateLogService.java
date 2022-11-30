package com.latrell.test.service;

import com.latrell.test.domain.OperateLog;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统操作日志记录 服务类
 * </p>
 *
 * @author liz
 * @since 2022-10-11
 */
public interface OperateLogService extends IService<OperateLog> {

    void saveOperateLog(List<OperateLog> operateLogs);

}
