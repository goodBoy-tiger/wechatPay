package com.gotop.wechatPay;

import com.gotop.wechatPay.domain.User;
import com.gotop.wechatPay.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.junit.Test;

/**
 * @ClassName CommonTest
 * @Description TOO
 * @Author 吕哥
 * @Date 2019/1/6 17:37
 */
public class CommonTest {

    @Test
    public void testGeneJwt(){
        User user = new User();
        user.setId(999);
        user.setName("xd");
        user.setHeadImg("www.xdclass.net");
       String token = JwtUtils.geneJsonWebToken(user);
       System.out.println(token);
    }

    @Test
    public void testCheck(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ4ZGNsYXNzIiwiaWQiOjk5OSwibmFtZSI6InhkIiwiaW1nIjoid3d3LnhkY2xhc3MubmV0IiwiaWF0IjoxNTQ2NzY3NjA5LCJleHAiOjE1NDczNzI0MDl9.i6SRIvfmRVhdVuqbKEi_yQjlBEA3WTsDFEPsXeOSLk4";
        Claims claims = JwtUtils.checkJWT(token);
        if(claims != null){
            String name = (String) claims.get("name");
            String img = (String) claims.get("img");
            int id = (Integer) claims.get("id");
            System.out.println(name);
            System.out.println(img);
            System.out.println(id);
        }else{
            System.out.println("非法token");
        }
    }
}
