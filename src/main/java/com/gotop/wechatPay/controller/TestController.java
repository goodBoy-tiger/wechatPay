package com.gotop.wechatPay.controller;

import com.gotop.wechatPay.config.WeChatConfig;
import com.gotop.wechatPay.domain.JsonData;
import com.gotop.wechatPay.domain.Video;
import com.gotop.wechatPay.mapper.VideoMapper;
import com.gotop.wechatPay.service.VideoService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName TestController
 * @Description TOO
 * @Author 吕哥
 * @Date 2019/1/3 11:37
 */
@RestController
@RequestMapping("/api")
public class TestController {

    @Autowired
    private WeChatConfig weChatConfig;
    @GetMapping("test_config")
    public Object test1(){
        System.out.println(weChatConfig.getAppId());
        return JsonData.buildSuccess(weChatConfig.getAppId());
    }


    @Autowired
    private VideoService videoService;
    @GetMapping("test_mapper")
    public Object findAll(){
        return videoService.findAll();
    }

    @GetMapping("test_findById")
    public Object findById(int id){
        return videoService.findById(id);
    }

    @Delete("test_delete")
    public Object delete(int id){
        return videoService.delete(id);
    }

    @PutMapping("test_update")
    public Object update(@RequestBody Video video){
        return videoService.update(video);
    }
}
