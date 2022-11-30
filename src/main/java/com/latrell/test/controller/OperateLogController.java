package com.latrell.test.controller;


import com.latrell.common.config.spring.aspect.log.ApiLog;
import com.latrell.common.domain.response.CommonResult;
import com.latrell.test.domain.OperateLog;
import com.latrell.test.service.OperateLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 系统操作日志记录 前端控制器
 * </p>
 *
 * @author liz
 * @since 2022-10-11
 */
@RestController
@RequestMapping("/api/operateLog")
@Api(value = "系统操作日志记录 前端控制器")
public class OperateLogController {

    @Resource
    private OperateLogService operateLogService;

    @ApiOperation(value = "新增系统操作日志记录")
    @ApiLog(value = "新增系统操作日志记录")
    @PostMapping("/save")
    public CommonResult<Object> saveOperateLog(@RequestBody List<OperateLog> operateLogs) {
        operateLogService.saveOperateLog(operateLogs);
        return new CommonResult<>("null", "新增系统操作记录");
    }

}

