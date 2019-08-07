package com.gotop.wechatPay;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableTransactionManagement //启注解事务管理
@SpringBootApplication
@MapperScan("com.gotop.wechatPay.mapper") //开启mapper扫描
public class WechatPayApplication {

	public static void main(String[] args) {
		SpringApplication.run(WechatPayApplication.class, args);
	}

}

