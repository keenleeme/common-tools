package com.latrell.test.mapper;

import com.latrell.test.domain.OperateLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统操作日志记录 Mapper 接口
 * </p>
 *
 * @author liz
 * @since 2022-10-11
 */
@Mapper
public interface OperateLogMapper extends BaseMapper<OperateLog> {

}
