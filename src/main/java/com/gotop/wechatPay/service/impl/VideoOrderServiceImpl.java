package com.gotop.wechatPay.service.impl;

import com.gotop.wechatPay.config.WeChatConfig;
import com.gotop.wechatPay.domain.User;
import com.gotop.wechatPay.domain.Video;
import com.gotop.wechatPay.domain.VideoOrder;
import com.gotop.wechatPay.dto.VideoOrderDto;
import com.gotop.wechatPay.mapper.UserMapper;
import com.gotop.wechatPay.mapper.VideoMapper;
import com.gotop.wechatPay.mapper.VideoOrderMapper;
import com.gotop.wechatPay.service.VideoOrderService;
import com.gotop.wechatPay.utils.CommonUtils;
import com.gotop.wechatPay.utils.HttpUtils;
import com.gotop.wechatPay.utils.WXPayUtil;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @ClassName VideoOrderServiceImpl
 * @Description TOO
 * @Author 吕哥
 * @Date 2019/5/9 15:32
 */
@Service
public class VideoOrderServiceImpl implements VideoOrderService {

    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private VideoOrderMapper videoOrderMapper;
    @Autowired
    private WeChatConfig weChatConfig;
    @Override
    public String save(VideoOrderDto videoOrderDto) throws Exception {

        //查找视频信息
        Video video = videoMapper.findById(videoOrderDto.getVideoId());
        //查找用户信息
        User user = userMapper.findByid(videoOrderDto.getUserId());
        //生成订单
        VideoOrder videoOrder = new VideoOrder();
        videoOrder.setTotalFee(video.getPrice());
        videoOrder.setVideoImg(video.getCoverImg());
        videoOrder.setVideoTitle(video.getTitle());
        videoOrder.setCreateTime(new Date());
        videoOrder.setVideoId(video.getId());

        videoOrder.setState(0);
        videoOrder.setUserId(user.getId());
        videoOrder.setHeadImg(user.getHeadImg());
        videoOrder.setNickname(user.getName());

        videoOrder.setDel(0);
        videoOrder.setIp(videoOrderDto.getIp());
        videoOrder.setOutTradeNo(CommonUtils.generateUUID());

        videoOrderMapper.insert(videoOrder);

        String payXml = unifiedOrder(videoOrder);
        //统一下单
        String orderStr = HttpUtils.doPost(weChatConfig.getUnifiedOrderUrl(),payXml,4000);
        if(null == orderStr){
            return null;
        }

        Map<String,String> unifiedOrderMap = WXPayUtil.xmlToMap(orderStr);
        if(unifiedOrderMap != null){
            return unifiedOrderMap.get("code_url");
        }
        return null;
    }

    @Override
    public VideoOrder findByOutTradeNo(String outTradeNo) {
        return videoOrderMapper.findByOutTradeNo(outTradeNo);
    }

    @Override
    public int updateOrderByOutTradeNo(VideoOrder videoOrder) {
        return videoOrderMapper.updateOrderByOutTradeNo(videoOrder);
    }

    private String unifiedOrder(VideoOrder videoOrder) throws Exception {

        //生成签名
        SortedMap<String,String> params = new TreeMap<>();
        params.put("appid",weChatConfig.getAppId());
        params.put("mch_id",weChatConfig.getMchId());
        params.put("nonce_str",CommonUtils.generateUUID());
        params.put("body",videoOrder.getVideoTitle());
        params.put("out_trade_no",videoOrder.getOutTradeNo());
        params.put("spbill_create_ip",videoOrder.getIp());
        params.put("notify_url",weChatConfig.getPayCallbackUrl());
        params.put("trade_type","NATIVE");

        //sign签名
        String sign = WXPayUtil.createSign(params,weChatConfig.getKey());
        params.put("sign",sign);
        //map转xml
        String payXml = WXPayUtil.mapToXml(params);

        return payXml;
    }
}
