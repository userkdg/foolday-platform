package com.foolday.dao.user;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.TableName;
import com.foolday.common.base.BaseEntity;
import com.foolday.common.enums.UserStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@TableName("t_user")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserEntity extends BaseEntity<UserEntity> {
    /*
    客户名称
     */
    private String name;
    /*
    头像
     */
    private String imgId;
    /*
    微信id
     */
    private String wxid;

    /**
     * 微信用户的一个openId对应一个公众号
     */
    private String openId;

    /**
     * 微信用户的唯一id，在用户的任何一个公众号上都一样
     */
    private String unionId;

    /*
    userInfo.getCity();
    userInfo.getCountry();
    userInfo.getGender();
    userInfo.getProvince();
     */
    private String city;

    private String country;

    private String province;

    private String gender;

    /**
     * 地区
     */
    private String countryCode;

    /**
     * 微信手机号码
     */
    private String tel;
    /*
    状态
     */
    @EnumValue
    private UserStatus status;
    /*
    用户在哪个店铺登录
     */
    private String shopId;
}
