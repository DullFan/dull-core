package com.dullfan.framework.web.exception;

import com.dullfan.common.constant.HttpStatus;
import com.dullfan.common.entity.vo.Result;
import com.dullfan.common.exception.ServiceException;
import com.dullfan.common.utils.StringUtils;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.UnexpectedTypeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.BindException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;


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
        return StringUtils.isNotNull(code) ? Result.error(code, e.getMessage()) : Result.error(e.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public Result handleBindException(BindException e) {
        log.error(e.getMessage(), e);
        return Result.error(HttpStatus.ERROR, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        log.error(e.getMessage(), e);
        return Result.error(HttpStatus.ERROR, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        String message = allErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(";"));
        return Result.error(HttpStatus.ERROR, message);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public Result handleConstraintViolationException(ConstraintViolationException e) {
        log.error(e.getMessage(), e);
        return Result.error(HttpStatus.ERROR, e.getMessage());
    }

    @ExceptionHandler(SQLException.class)
    public Result handleConstraintViolationException(SQLException e) {
        log.error(e.getMessage(), e);
        return Result.error(HttpStatus.ERROR, e.getMessage());
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result handleConstraintViolationException(HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage(), e);
        return Result.error(HttpStatus.ERROR, "该请求不存在");
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    public Result handleUnexpectedTypeException(UnexpectedTypeException e) {
        log.error(e.getMessage(), e);
        return Result.error(HttpStatus.ERROR, e.getMessage());
    }

}
