package com.latrell.common.config.exception;

import com.latrell.common.domain.response.ReturnCodeEnum;

import java.io.Serializable;

/**
 * 统一异常处理类
 *
 * @author liz
 * @date 2022/10/19-15:10
 */
public class CommonException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -6343525667986009928L;

    private Integer code;
    private String message;

    public CommonException() {
    }

    public CommonException(String message) {
        super(message);
        this.code = ReturnCodeEnum.FAIL.getValue();
        this.message = message;
    }

    public CommonException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private static class Builder {

        private final CommonException commonException;

        public Builder() {
            this.commonException = new CommonException();
        }

        public Builder code(Integer code) {
            commonException.code = code;
            return this;
        }

        public Builder message(String message) {
            commonException.message = message;
            return this;
        }

        public CommonException build() {
            return commonException;
        }

    }

    public static CommonException buildException(Integer code, String message, Throwable cause) {
        CommonException commonException = new Builder().code(code).message(message).build();
        commonException.initCause(cause);
        return commonException;
    }

}
