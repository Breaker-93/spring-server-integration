package com.breaker.ssi.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDate;
import com.breaker.ssi.utils.entity.CommonEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Breaker-93
 * @since 2020-05-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_user_info")
public class SysUserInfo extends CommonEntity {


    /**
     * 用户id
     */
    @TableField("USER_ID")
    private String userId;

    /**
     * 昵称（50个字符以内）
     */
    @TableField("NICK_NAME")
    private String nickName;

    /**
     * 真实姓名（50个字符以内）
     */
    @TableField("REAL_NAME")
    private String realName;

    /**
     * 性别（1：男；0：女; 2: 未设置）
     */
    @TableField("SEX")
    private Integer sex;

    /**
     * 手机号
     */
    @TableField("PHONE")
    private String phone;

    /**
     * 简介/座右铭（255个字符以内）
     */
    @TableField("PROFILE")
    private String profile;

    /**
     * 头像
     */
    @TableField("HEAD_IMG")
    private String headImg;

    /**
     * 微信号
     */
    @TableField("WECHAT")
    private String wechat;

    /**
     * 邮箱
     */
    @TableField("EMAIL")
    private String email;

    /**
     * 家乡（各省市的唯一编号）
     */
    @TableField("HOMETOWN")
    private String hometown;

    /**
     * 出生年月（通过此计算当前年龄）
     */
    @TableField("BIRTHDAY")
    private LocalDate birthday;


}
