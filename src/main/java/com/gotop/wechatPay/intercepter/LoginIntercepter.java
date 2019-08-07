package com.gotop.wechatPay.intercepter;

import com.google.gson.Gson;
import com.gotop.wechatPay.domain.JsonData;
import com.gotop.wechatPay.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @ClassName LoginIntercepter
 * @Description TOO
 * @Author 吕哥
 * @Date 2019/1/22 14:43
 */
public class LoginIntercepter implements HandlerInterceptor {
    private static final Gson gson = new Gson();

    /**
     * 进入controller之前进行拦截
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if(token == null){
            token = request.getParameter("token");
        }
        if(token != null){
            Claims claims = JwtUtils.checkJWT(token);
            if(claims != null){
                Integer id = (Integer) claims.get("id");
                String name = (String)claims.get("name");
                request.setAttribute("user_id",id);
                request.setAttribute("name",name);
                //普通用户
                return true;
            }

        }
        sendJsonMessage(response, JsonData.buildError("请登入"));
        return false;
    }

    /**
     * 响应前端数据
     * @param response
     * @param obj
     * @throws Exception
     */
    public static void sendJsonMessage(HttpServletResponse response, Object obj) throws Exception {
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print(gson.toJson(obj));
        writer.close();
        response.flushBuffer();
    }
}
