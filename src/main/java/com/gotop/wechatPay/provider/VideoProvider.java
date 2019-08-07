package com.gotop.wechatPay.provider;

import com.gotop.wechatPay.domain.Video;
import org.apache.ibatis.jdbc.SQL;

/**
 * @ClassName VideoProvider
 * @Description 动态构建sql语句
 * @Author 吕哥
 * @Date 2019/1/3 21:54
 */

public class VideoProvider {

    /**
     * 更新video动态sql语句
     * @param video
     * @return
     */
    public String updateVideo(final Video video){
        return new SQL(){{
            UPDATE("vodie");

            //条件写法
            if(video.getTitle()!=null){
                SET("title=#{title}");
            }
            if(video.getSummary()!=null){
                SET("summart=#{summart}");
            }
            if(video.getCoverImg()!=null){
                SET("cover_img=#{coverImg}");
            }
            if(video.getViewNum()!=null){
                SET("view_num=#{viewNum}");
            }
            if(video.getOnline()!=null){
                SET("online=#{online}");
            }
            if(video.getPrice()!=null){
                SET("price=#{price}");
            }
            if (video.getPoint()!=null){
                SET("point=#{point}");
            }
            WHERE("id=#{id}");
        }}.toString();
    }
}
