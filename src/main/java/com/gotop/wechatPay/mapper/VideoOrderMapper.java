package com.gotop.wechatPay.mapper;

import com.gotop.wechatPay.domain.VideoOrder;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @ClassName 订单dao层
 * @Description TOO
 * @Author 吕哥
 * @Date 2019/5/9 9:15
 */
public interface VideoOrderMapper {
    /**
     * 保存订单，返回主键
     *
     * @param videoOrder
     * @return
     */
    @Insert("INSERT INTO `wechatpay`.`video_order` ( `openid`, `out_trade_no`, `state`, " +
            "`create_time`, `notify_time`, `total_fee`, `nickname`, `head_img`, " +
            "`video_id`, `video_title`, `video_img`, `user_id`, `ip`, `del`) " +
            "VALUES " +
            "(#{openid},#{outTradeNo},#{state},#{createTime},#{notifyTime},#{totalFee},#{nickname},#{headImg}," +
            "#{videoId},#{videoTitle},#{videoImg},#{userId},#{ip},#{del});")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(VideoOrder videoOrder);

    /**
     * 根据主键获取订单对象
     *
     * @param id
     * @return
     */
    @Select("select * from video_order where id = #{order_id} and del=0")
    VideoOrder findById(@Param(value = "order_id") int id);


    /**
     * 根据交易订单号获取订单对象
     *
     * @param outTradeNo
     * @return
     */
    @Select("select * from video_order where out_trade_no = #{out_trade_no} and del=0")
    VideoOrder findByOutTradeNo(@Param("out_trade_no") String outTradeNo);

    /**
     * 逻辑删除订单
     *
     * @param id
     * @param userId
     * @return
     */
    @Update("update video_order set del = 1 where id = #{id} and user_id = #{userId}")
    int del(@Param("id") int id, @Param("userId") int userId);

    /**
     * 查找我的全部订单
     * @param userId
     * @return
     */
    @Select("select * from video_order where user_id = #{userId}")
    List<VideoOrder> findMyOrderList(int userId);

    /**
     * 根据订单流水号更新
     * @param videoOrder
     * @return
     */
    @Update("update video_order set state = #{state},notify_time = #{notifyTime},openid = #{openid} " +
            "where out_trade_no = #{outTradeNo} and del = 0 and state = 0}")
    int updateOrderByOutTradeNo(VideoOrder videoOrder);
}

