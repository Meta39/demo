package com.fu.demo;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.fu.demo.entity.Err;
import com.fu.demo.entity.Res;
import com.fu.demo.entity.Status;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler{
    private final static Logger log = new LoggerContext().getLogger(GlobalExceptionHandler.class);

    /**
     * 自定义异常
     */
    @ExceptionHandler(value =Err.class)
    public Res Err(Err e){
        return Res.err(e.getStatus(), e.getError(),e.getMessage());
    }

    /**
     * 空指针异常
     */
    @ExceptionHandler(value =NullPointerException.class)
    public Res exceptionHandler(NullPointerException e){
        log.error("空指针异常",e);
        return Res.err(Status.NULL_POINTER_EXCEPTION.getStatus(),Status.NULL_POINTER_EXCEPTION.getError());
    }

    /**
     * 5000异常
     */
    @ExceptionHandler(value =Exception.class)
    public Res exception(Exception e){
        log.error("5000异常：",e);
        return Res.err(Status.FAIL.getStatus(), Status.FAIL.getError(),e.getMessage());
    }

    /**
     * 请求参数异常
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Res missingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("缺少请求参数{}",e.getParameterName());
        return Res.err(Status.MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION.getStatus(), Status.MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION.getError(),e.getParameterName());
    }

    /**
     * 参数校验异常
     */
    @ExceptionHandler(BindException.class)
    public Res bindException(BindException e) {
        log.error("参数校验异常{}", Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
        return Res.err(Status.BIND_EXCEPTION.getStatus(),Status.BIND_EXCEPTION.getError(),e.getBindingResult().getFieldError().getDefaultMessage());
    }

    /**
     * 数据库异常
     */
    @ExceptionHandler(MyBatisSystemException.class)
    public Res MyBatisSystemException(MyBatisSystemException e) {
        log.error("数据库异常（有可能是连接不上数据库）{}",e.getMessage());
        return Res.err(Status.MYBATIS_SYSTEM_EXCEPTION.getStatus(), Status.MYBATIS_SYSTEM_EXCEPTION.getError(),e.getMessage());
    }

}
