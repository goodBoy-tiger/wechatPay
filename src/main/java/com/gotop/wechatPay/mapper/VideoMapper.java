package com.gotop.wechatPay.mapper;

import com.gotop.wechatPay.domain.Video;
import com.gotop.wechatPay.provider.VideoProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @ClassName VideoMapper
 * @Description video数据访问层
 * @Author 吕哥
 * @Date 2019/1/3 14:47
 */
public interface VideoMapper {

    @Select("select * from video")
    /*通过在applicate.properties增加下划线转驼峰配置就可以省去这繁琐操作（开发技巧）
    @Results({
            @Result(column = "cover_img",property = "coverImg"),
            @Result(column = "create_time",property = "createTime")
    })*/
    List<Video> findAll();

    @Select("SELECT * FROM video WHERE id = #{id}")
    Video findById(int id);

    /*@Update("UPDATE video SET title=#{title} WHERE id =#{id}")*/
    @UpdateProvider(type = VideoProvider.class,method = "updateVideo")
    int update(Video Video);

    @Delete("DELETE FROM video WHERE id =#{id}")
    int delete(int id);

    @Insert("INSERT INTO `video` ( `title`, `summary`, " +
            "`cover_img`, `view_num`, `price`, `create_time`," +
            " `online`, `point`)" +
            "VALUES" +
            "(#{title}, #{summary}, #{coverImg}, #{viewNum}, #{price},#{createTime}" +
            ",#{online},#{point});")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    int save(Video video);
}
