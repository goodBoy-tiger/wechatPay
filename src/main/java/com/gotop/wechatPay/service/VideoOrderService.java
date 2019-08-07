package com.gotop.wechatPay.service;

import com.gotop.wechatPay.domain.VideoOrder;
import com.gotop.wechatPay.dto.VideoOrderDto;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName 订单接口
 * @Description TOO
 * @Author 吕哥
 * @Date 2019/5/9 15:27
 */
public interface VideoOrderService {

    /**
     * 下单接口
     * @param videoOrderDto
     * @return
     */
    String save(VideoOrderDto videoOrderDto) throws Exception;

    /**
     * 根据流水号查找订单
     * @param outTradeNo
     * @return
     */
    VideoOrder findByOutTradeNo(String outTradeNo);

    /**
     * 根据流水号更新订单
     * @param videoOrder
     * @return
     */
    int updateOrderByOutTradeNo(VideoOrder videoOrder);
}
