package com.gotop.wechatPay.service.impl;

import com.gotop.wechatPay.config.WeChatConfig;
import com.gotop.wechatPay.domain.User;
import com.gotop.wechatPay.mapper.UserMapper;
import com.gotop.wechatPay.service.UserService;
import com.gotop.wechatPay.utils.HttpUtils;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

/**
 * @ClassName UserServiceIml
 * @Description TOO
 * @Author 吕哥
 * @Date 2019/1/14 17:49
 */
@Service
public class UserServiceIml implements UserService {

    @Autowired
    private WeChatConfig weChatConfig;
    @Autowired
    private UserMapper userMapper;

    @Override
    public User saveWechatUser(String code) {

        /**
         * 获取access_token
         */
        String accessTokenUrl = String.format(WeChatConfig.getOpenAccessTokenUrl(),weChatConfig.getOpenAppid(),weChatConfig.getOpenAppsecret(),code);
        Map<String,Object> baseMap = HttpUtils.doGet(accessTokenUrl);

        if(baseMap == null || baseMap.isEmpty()){
            return null;
        }
        String accessToken = (String)baseMap.get("access_token");
        String openid = (String)baseMap.get("openid");

        /**
         * 如果数据库中已有用户信息则不去请求获取用户信息
         */
        User dbUser = userMapper.findByopenid(openid);
        if(dbUser != null){ //更新用户，或者直接返回
            return dbUser;
        }

        /**
         * 获取微信用户信息
         */
        String userInfoUrl = String.format(WeChatConfig.getOpenUserInfoUrl(),accessToken,openid);
        Map<String,Object> baseUserMap = HttpUtils.doGet(userInfoUrl);
        if (baseMap == null || baseMap.isEmpty()){
            return null;
        }
        String nickname = (String)baseUserMap.get("nickname");
        Double sexTemp = (Double)baseUserMap.get("sex");
        int sex = sexTemp.intValue();
        String country = (String)baseUserMap.get("country");
        String province = (String)baseUserMap.get("province");
        String city = (String)baseUserMap.get("city");
        String headimgurl = (String)baseUserMap.get("headimgurl");

        StringBuilder sb = new StringBuilder(country).append("||").append(province).append("||").append(city);
        String finalAddress = sb.toString();
        try {
            //解决乱码
            nickname = new String(nickname.getBytes("ISO-8859-1"),"UTF-8");
            finalAddress = new String(finalAddress.getBytes("ISO-8859-1"),"UTF-8");
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

        /**
         * 保存用户信息
         */
        User user = new User();
        user.setName(nickname);
        user.setHeadImg(headimgurl);
        user.setSex(sex);
        user.setCity(finalAddress);
        user.setOpenid(openid);
        user.setCreateTime(new Date());
        userMapper.save(user);
        return user;
    }
}
