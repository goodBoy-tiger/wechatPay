package com.gotop.wechatPay.service;

import com.gotop.wechatPay.domain.User;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserService
 * @Description 用户业务接口类
 * @Author 吕哥
 * @Date 2019/1/14 17:47
 */
@Service
public interface UserService {

    User saveWechatUser(String code);
}
