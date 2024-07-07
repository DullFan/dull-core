package com.dullfan.framework.web.exception;

import com.dullfan.common.domain.vo.Result;
import com.dullfan.common.exception.ServiceException;
import com.dullfan.common.utils.StringTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 全局异常处理器
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public Result handleServiceException(ServiceException e) {
        log.error(e.getMessage(), e);
        Integer code = e.getCode();
        return StringTools.isNotNull(code) ? Result.error(code, e.getMessage()) : Result.error(e.getMessage());
    }

}
