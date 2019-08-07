package com.gotop.wechatPay.exception;

import lombok.Data;

/**
 * @ClassName XdException
 * @Description 自定义异常了
 * @Author 吕哥
 * @Date 2019/5/14 14:14
 */
@Data
public class XdException extends RuntimeException {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 异常消息
     */
    private String msg;

    public XdException() {
        super();
    }

    public XdException(Integer code,String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
