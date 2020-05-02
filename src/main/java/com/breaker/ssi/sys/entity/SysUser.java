package com.breaker.ssi.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2020-05-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_user")
public class SysUser extends CommonEntity {


    /**
     * 用户名
     */
    @TableField("USERNAME")
    private String username;

    /**
     * 密码
     */
    @TableField("PASSWORD")
    private String password;


}
