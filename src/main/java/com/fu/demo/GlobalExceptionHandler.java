package com.fu.demo;

import com.fu.demo.entity.Err;
import com.fu.demo.entity.Res;
import org.mybatis.spring.MyBatisSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@RestControllerAdvice
public class GlobalExceptionHandler{
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 打印详细异常日志信息
     */
    public static String getExceptionDetail(Exception ex) {
        String ret = null;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PrintStream pout = new PrintStream(out);
            ex.printStackTrace(pout);
            ret = out.toString();
            pout.close();
            out.close();
        } catch (Exception ignored) {}
        return ret;
    }

    /**
     * 自定义异常
     */
    @ExceptionHandler(value =Err.class)
    public Res Err(Err e){
        return e.getCode() == 0 ? Res.err(e.getMsg()) : Res.err(e.getCode(), e.getMsg());
    }

    /**
     * 空指针异常
     */
    @ExceptionHandler(value =NullPointerException.class)
    public Res exceptionHandler(NullPointerException e){
        log.error("空指针异常"+getExceptionDetail(e));
        return Res.err("空指针异常：\n"+getExceptionDetail(e));
    }

    /**
     * 其他异常
     */
    @ExceptionHandler(value =Exception.class)
    public Res exception(Exception e){
        log.error("500异常："+getExceptionDetail(e));
        return Res.err("500异常：\n"+getExceptionDetail(e));
    }

    /**
     * 参数校验异常
     */
    @ExceptionHandler(BindException.class)
    public Res bindException(BindException e) {
        // 然后提取错误提示信息进行返回
        log.error("参数校验异常："+e.getBindingResult().getFieldError().getDefaultMessage());
        return Res.err("参数校验异常："+e.getBindingResult().getFieldError().getDefaultMessage());
    }

    /**
     * 请求参数异常
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Res missingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("请求参数异常"+e);
        return Res.err("缺少请求参数："+e.getParameterName());
    }
    
    /**
     * 数据库异常
     * @param e
     * @return
     */
    @ExceptionHandler(MyBatisSystemException.class)
    public Res MyBatisSystemException(MyBatisSystemException e) {
        log.error(e.getMessage());
        return Res.err("数据库异常（有可能是连接不上数据库）："+e.getMessage());
    }
    
}
