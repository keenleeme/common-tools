package com.latrell.common.domain.response;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * 返回值枚举类
 *
 * @author liz
 * @date 2022/10/11-14:10
 */
public enum ReturnCodeEnum {

    SUCCESS(0),
    FAIL(-1),
    /**
     * 系统异常(所有异常都无法识别之后的兜底，业务代码中不可直接抛出此异常)
     */
    SYSTEM_EXCEPTION(9999),
    /**
     * 接口入参数据异常
     */
    INTERFACE_INPUT_PARAMETER_EXCEPTION(1000),
    /**
     * 数据异常
     */
    DATA_ERROR(1001),
    /**
     * 操作不可执行
     */
    OPERATION_NOT_EXECUTABLE(1002),
    /**
     * 配置项不存在
     */
    CONFIG_NOT_EXISTS(1003),
    /**
     * 时间范围错误
     */
    TIME_RANGE_ILLEGAL(1004),
    /**
     * 权限被拒绝
     */
    DENIED_PERMISSION(1005),
    /**
     * 1分钟内接口请求次数过量
     */
    EXCESSIVE_API_REQUEST_IN_ONE_MINUTE(1006);

    private final int value;
    public static Map<Integer, ReturnCodeEnum> map = new HashMap<Integer, ReturnCodeEnum>();

    static {
        for (ReturnCodeEnum e : EnumSet.allOf(ReturnCodeEnum.class)) {
            ReturnCodeEnum codeEnum = map.put(e.value, e);
        }
    }

    ReturnCodeEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static ReturnCodeEnum getEnum(int index) {
        return map.get(index);
    }

}
