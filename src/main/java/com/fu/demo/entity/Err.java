package com.fu.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Err extends RuntimeException{
    private int code;
    private String msg;

    /**
     * 默认状态码为0，普通异常
     * @param message 错误信息
     */
    public Err(String message){
        this.msg = message;
    }

    /**
     * 自定义异常和状态码
     * @param code 自定义的状态码，如：登录为-1等
     * @param message 错误信息
     */
//    public Err(Integer code,String message){}
}