package com.gotop.wechatPay.config;

import com.gotop.wechatPay.intercepter.LoginIntercepter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**拦截器配置
 * @ClassName InterceptorConfig
 * @Description TOO
 * @Author 吕哥
 * @Date 2019/1/22 14:59
 */
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginIntercepter()).addPathPatterns("/user/api/v1/*/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
