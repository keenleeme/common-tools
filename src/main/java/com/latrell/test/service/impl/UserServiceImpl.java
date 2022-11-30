package com.latrell.test.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.latrell.test.domain.SysUser;
import com.latrell.test.mapper.UserMapper;
import com.latrell.test.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author liz
 * @since 2022-10-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, SysUser> implements UserService {

    @Override
    public List<SysUser> getUserListByUserName(String userName) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<SysUser>().eq(SysUser::getUserName, userName);
        return this.list(wrapper);
    }

    @Override
    public SysUser saveUser(SysUser sysUser) {
        this.baseMapper.insert(sysUser);
        return sysUser;
    }

    @Override
    public List<SysUser> getListById(String userId) {
        return baseMapper.queryListById(Long.parseLong(userId));
    }
}
