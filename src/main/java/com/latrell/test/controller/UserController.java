package com.latrell.test.controller;

import com.latrell.annotation.DoDoor;
import com.latrell.common.config.spring.aspect.limit.FlowLimit;
import com.latrell.common.config.spring.aspect.log.ApiLog;
import com.latrell.common.config.spring.aspect.log.ApiLogContext;
import com.latrell.common.domain.constant.CommonConstants;
import com.latrell.common.domain.enums.OpModule;
import com.latrell.common.domain.enums.OpType;
import com.latrell.common.domain.response.CommonResult;
import com.latrell.test.domain.SysUser;
import com.latrell.test.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author liz
 * @since 2022-10-11
 */
@RestController
@RequestMapping("/api/user")
@Api(value = "用户信息表 前端控制器")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/getList")
    @ApiOperation(value = "查询用户列表集合")
    @FlowLimit
    @ApiLog(value = "查询用户列表集合")
    public CommonResult<List<SysUser>> getList() {
        List<SysUser> list = userService.list();
        ApiLogContext.put(CommonConstants.OP_RESULT, 1);
        ApiLogContext.put(CommonConstants.DETAIL_MESSAGE, "查询用户列表成功");
        return new CommonResult<>(list);
    }

    @GetMapping("/getListById")
    @ApiOperation(value = "根据用户id查询用户列表集合")
    @DoDoor(key = "userId", returnJson = "{\"code\": -1, \"message\": \"非白名单可访问用户拦截！\"}")
    @ApiLog(value = "根据用户id查询用户列表集合")
    public CommonResult<List<SysUser>> getListById(@RequestParam String userId) {
        List<SysUser> list = userService.getListById(userId);
        ApiLogContext.put(CommonConstants.OP_RESULT, 1);
        ApiLogContext.put(CommonConstants.DETAIL_MESSAGE, "查询用户列表成功");
        return new CommonResult<>(list);
    }

    @GetMapping("/getListByUserName")
    @ApiOperation(value = "根据用户名称查询用户列表集合")
    @ApiLog(value = "根据用户名称查询用户列表集合", module = OpModule.USER_MANAGE, opType = OpType.QUERY)
    public CommonResult<List<SysUser>> getListByUserName(@RequestParam String userName) {
        List<SysUser> userList = userService.getUserListByUserName(userName);
        ApiLogContext.put(CommonConstants.OP_RESULT, 1);
        ApiLogContext.put(CommonConstants.DETAIL_MESSAGE, "根据用户名称查询用户列表集合成功");
        return new CommonResult<>(userList);
    }

    @PostMapping("/save")
    @ApiOperation(value = "保存用户信息")
    @ApiLog(value = "保存用户信息", module = OpModule.USER_MANAGE, opType = OpType.INSERT)
    public CommonResult<SysUser> saveUser(@RequestBody SysUser sysUser) {
        SysUser saveUser = userService.saveUser(sysUser);
        ApiLogContext.put(CommonConstants.OP_RESULT, 1);
        ApiLogContext.put(CommonConstants.DETAIL_MESSAGE, "保存用户信息成功");
        return new CommonResult<>(saveUser);
    }

}

