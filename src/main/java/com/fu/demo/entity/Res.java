package com.fu.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Res<T>{
    private Integer code;
    private String msg;
    private T data;

    /**
     * 成功返回
     * @param data 数据
     */
    public Res(T data) {
        this.code = Code.SUCCESS.getNum();
        this.msg = Code.SUCCESS.getMsg();
        this.data = data;
    }

    /**
     * 普通异常，如：0自定义异常等
     * @param msg
     * @return
     */
    public static Res err(String msg) {
        Res res = new Res();
        res.setCode(Code.FAIL.getNum());
        res.setMsg(msg);
        return res;
    }

    /**
     * 特殊异常，如：-1未登录异常，404找不到页面异常等
     * @param code 异常码
     * @param msg 异常信息
     * @return
     */
    public static Res err(Integer code,String msg) {
        Res res = new Res();
        res.setCode(code);
        res.setMsg(msg);
        return res;
    }
}
