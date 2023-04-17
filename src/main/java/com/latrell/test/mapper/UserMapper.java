package com.latrell.test.mapper;

import com.latrell.test.domain.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author liz
 * @since 2022-10-11
 */
@Mapper
public interface UserMapper extends BaseMapper<SysUser> {

    List<SysUser> queryListById(@Param("userId") Long userId);

    List<SysUser> queryListById(@Param("userId") Long userId, @Param("userName") String userName);

}
