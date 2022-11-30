package com.latrell.test.service;

import com.latrell.test.domain.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author liz
 * @since 2022-10-11
 */
public interface UserService extends IService<SysUser> {

    List<SysUser> getUserListByUserName(String userName);

    SysUser saveUser(SysUser sysUser);

    List<SysUser> getListById(String userId);

}
