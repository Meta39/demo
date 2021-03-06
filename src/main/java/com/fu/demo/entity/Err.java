package com.fu.demo.entity;

import lombok.*;

import java.io.Serializable;

/**
 * 自定义异常类
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Err extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 7558796578827818466L;
    private int status = 5000;//默认错误状态码为500
    private String error;//固定错误信息
    private String message;//自定义错误信息
    private String path;//路径

    /**
     * 默认普通异常
     * @param error 错误信息
     */
    public Err(String error){
        this.error = error;
    }

    /**
     * Status配置的固定错误信息
     * @param status 状态码
     * @param error 固定错误信息
     */
    public Err(int status,String error){
        this.status = status;
        this.error = error;
    }

    /**
     * Status配置的固定错误信息+自定义错误信息
     * @param status 状态码
     * @param error 固定错误信息
     * @param message 自定义错误信息
     */
    public Err(int status,String error,String message){
        this.status = status;
        this.error = error;
        this.message =message;
    }

}