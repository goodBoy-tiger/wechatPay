package com.gotop.wechatPay.mapper;

import com.gotop.wechatPay.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @ClassName UserMapper
 * @Description TOO
 * @Author 吕哥
 * @Date 2019/1/15 11:22
 */
public interface UserMapper {
    /**
     * 根据id查询用户
     * @param userId
     * @return
     */
    @Select("select * from user where id = #{id}")
    User findByid(@Param(value = "id") int userId);

    /**
     * 根据openid查询用户
     * @param openid
     * @return
     */
    @Select("select * from user where openid = #{openid}")
    User findByopenid(@Param(value = "openid")String openid);

    /**
     * 保存用户信息
     * @param user
     * @return
     */
    @Insert("insert into user(openid,name,head_img,phone,sex,city,create_time)" +
            "values(#{openid},#{name},#{headImg},#{phone},#{sex},#{city},#{createTime})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    int save(User user);
}
