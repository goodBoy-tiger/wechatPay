package com.gotop.wechatPay.utils;

import com.gotop.wechatPay.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @ClassName JwtUtils
 * @Description jwt工具类  包括头部，负荷，签名， 头部和负荷经过base64编码，和秘钥再由hs256算法加密生产token
 * @Author 吕哥
 * @Date 2019/1/6 16:58
 */
public class JwtUtils {

    public static final String SUBJECT = "xdclass";
    public static final long EXPIRE = 1000*60*60*24*7; //过期时间，毫秒，一周
    public static final String APPSECRET = "xd666"; //秘钥

    /**
     * 生产jwt
     * @param user
     * @return
     */
    public static String geneJsonWebToken(User user){
        if(user.getId() == null || user.getName() == null || user.getHeadImg() ==null){
            return null;
        }
        String token = Jwts.builder().setSubject(SUBJECT).claim("id",user.getId())
                .claim("name",user.getName()).claim("img",user.getHeadImg())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRE))
                .signWith(SignatureAlgorithm.HS256,APPSECRET).compact();
        return token;
    }

    public static Claims checkJWT(String token){
        try {
            final Claims claims = Jwts.parser().setSigningKey(APPSECRET)
                    .parseClaimsJws(token).getBody();
            return claims;
        }catch (Exception e){}
        return null;
    }
}
