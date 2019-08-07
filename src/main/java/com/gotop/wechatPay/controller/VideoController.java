package com.gotop.wechatPay.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gotop.wechatPay.domain.Video;
import com.gotop.wechatPay.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName VideoController
 * @Description Video接口
 * @Author 吕哥
 * @Date 2019/1/5 23:41
 */
@RestController
@RequestMapping("/api/v1/video")
public class VideoController {
    @Autowired
    private VideoService videoService;

    /**
     * 分页接口
     *
     * @param page 当前第几页，默认是第一页
     * @param size 每页显示几条
     * @return
     */
    @GetMapping("page")
    public Object page(@RequestParam(value = "page", defaultValue = "1") int page,
                       @RequestParam(value = "size", defaultValue = "10") int size) {
        PageHelper.startPage(page,size);
        List<Video> list = videoService.findAll();
        PageInfo<Video> pageInfo = new PageInfo<>(list);
        Map<String,Object> map = new HashMap<>();
        map.put("total_size",pageInfo.getTotal());//总条数
        map.put("total_page",pageInfo.getPages());//总页数
        map.put("current_page",pageInfo.getPageNum());//当前页
        map.put("data",pageInfo.getList());
        return map;
    }

    /**
     * 根据id查找视频
     *
     * @param videoId
     * @return
     */
    @GetMapping("find_by_id")
    public Object findById(@RequestParam(value = "video_id", required = true) int videoId) {
        return videoService.findById(videoId);
    }


}