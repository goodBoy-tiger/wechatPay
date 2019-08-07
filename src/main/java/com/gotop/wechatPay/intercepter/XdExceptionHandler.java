package com.gotop.wechatPay.intercepter;

import com.gotop.wechatPay.domain.JsonData;
import com.gotop.wechatPay.exception.XdException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName XdExceptionHandler
 * @Description 异常处理控制器
 * @Author 吕哥
 * @Date 2019/5/14 14:25
 */
@ControllerAdvice
public class XdExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    public JsonData Handler(Exception e){

        if(e instanceof XdException){
            XdException xdException = (XdException)e;
            return JsonData.buildError(xdException.getMsg(),xdException.getCode());
        }else{
            return JsonData.buildError("全局异常未知错误！");
        }
    }
}
