package com.latrell.common.domain.response;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 通用的返回值类
 *
 * @author liz
 * @date 2022-10-11 14:12:06
 */
public class CommonResult<T> {

    public static final Logger LOG = LoggerFactory.getLogger(CommonResult.class);

    private int code = ReturnCodeEnum.SUCCESS.getValue();
    private String message = "成功";
    private String pageId = "common_tools";
    private T data;

    public CommonResult() {
    }

    public CommonResult(T data) {
        this.data = data;
        if (data == null) {
            code = ReturnCodeEnum.FAIL.getValue();
            message = "失败!";
        }
    }

    public CommonResult(String message) {
        this.code = ReturnCodeEnum.FAIL.getValue();
        this.message = message;
    }

    public CommonResult(T data, String message) {
        this.data = data;
        this.message = message;
    }

    /**
     * 如果data为false则说明出错了
     *
     * @param success
     */
    public CommonResult(Boolean success) {
        if (!success) {
            code = ReturnCodeEnum.FAIL.getValue();
            message = "失败!";
        } else {
            code = ReturnCodeEnum.SUCCESS.getValue();
        }
    }

    public CommonResult(T data, ReturnCodeEnum rce) {
        this.data = data;
        this.code = rce.getValue();
    }

    public CommonResult(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    public CommonResult(ReturnCodeEnum rce, String message) {
        this.message = message;
        this.code = rce.getValue();
    }

    public CommonResult(String checkName, String check, T resultCheck) {
        this.code = ReturnCodeEnum.FAIL.getValue();
        if (StrUtil.isEmpty(check)) {
            this.message = checkName + "不可为空";
        } else if (resultCheck == null) {
            this.message = "结果为null,可能有异常";
        } else {
            this.message = "成功";
            this.code = ReturnCodeEnum.SUCCESS.getValue();
            this.data = resultCheck;
        }
    }

    @Override
    public String toString() {
        try {
            return JSONObject.toJSONString(this);
        } catch (Exception e) {
            LOG.error("转换JSON出错", e);
        }
        return null;
    }

    public boolean isSuccess() {
        return this.code == 0;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
