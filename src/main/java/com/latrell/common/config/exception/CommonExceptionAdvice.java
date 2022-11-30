package com.latrell.common.config.exception;

import com.latrell.common.domain.response.CommonResult;
import com.latrell.common.domain.response.ReturnCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 接口异常处理器
 *
 * @author liz
 * @date 2022/10/19-15:37
 */
@ControllerAdvice
public class CommonExceptionAdvice {

    private static final Logger logger = LoggerFactory.getLogger(CommonExceptionAdvice.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonResult<Object> commonExceptionHandler(Exception e) {
        CommonResult<Object> commonResult;
        if (e instanceof CommonException) {
            CommonException commonException = (CommonException) e;
            logger.warn("{}", e);
            commonResult = new CommonResult<>(commonException.getCode(), commonException.getMessage());
        } else {
            logger.warn("web api unknown exception:{}", e);
            commonResult = new CommonResult<>(ReturnCodeEnum.FAIL, e.getMessage());
        }
        return commonResult;
    }

}
