package com.gotop.wechatPay.service.impl;

import com.gotop.wechatPay.domain.Video;
import com.gotop.wechatPay.mapper.VideoMapper;
import com.gotop.wechatPay.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName VideoServiceImpl
 * @Description TOO
 * @Author 吕哥
 * @Date 2019/1/3 16:54
 */
@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;

    @Override
    public List<Video> findAll() {
        return videoMapper.findAll();
    }

    @Override
    public Video findById(int id) {
        return videoMapper.findById(id);
    }

    @Override
    public int update(Video Video) {
        return videoMapper.update(Video);
    }

    @Override
    public int delete(int id) {
        return videoMapper.delete(id);
    }

    @Override
    public int save(Video video) {
        return videoMapper.save(video);
    }
}
