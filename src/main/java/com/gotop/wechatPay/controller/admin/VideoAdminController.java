package com.gotop.wechatPay.controller.admin;

import com.gotop.wechatPay.domain.Video;
import com.gotop.wechatPay.service.VideoService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName VideoAdminController
 * @Description TOO
 * @Author 吕哥
 * @Date 2019/1/6 0:36
 */
@RestController
@RequestMapping("/admin/v1/vedio/")
public class VideoAdminController {

    @Autowired
    private VideoService videoService;
    /**
     * 根据id删除视频
     *
     * @param videoId
     * @return
     */
    @Delete("del_by_id")
    public Object delById(@RequestParam(value = "video_id", required = true) int videoId) {
        return videoService.delete(videoId);
    }

    /**
     * 根据id更新视频
     *
     * @param video
     * @return
     */
    @PutMapping("update_by_id")
    public Object update(@RequestBody Video video) {
        return videoService.update(video);
    }

    /**
     * 保存视频对象
     * @param video
     * @return
     */
    public Object save(@RequestBody Video video){
        return videoService.save(video);
    }
}
