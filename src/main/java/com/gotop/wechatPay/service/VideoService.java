package com.gotop.wechatPay.service;

import com.gotop.wechatPay.domain.Video;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName VideoService
 * @Description TOO
 * @Author 吕哥
 * @Date 2019/1/3 16:50
 */
@Service
public interface VideoService {

    List<Video> findAll();

    Video findById(int id);

    int update(Video Video);

    int delete(int id);

    int save(Video video);
}
