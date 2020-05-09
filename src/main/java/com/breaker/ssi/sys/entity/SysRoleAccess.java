package com.breaker.ssi.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.breaker.ssi.utils.entity.CommonEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.breaker.ssi.utils.entity.IdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Breaker-93
 * @since 2020-05-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_role_access")
public class SysRoleAccess extends IdEntity {


    /**
     * 角色id
     */
    @TableField("ROLE_ID")
    private String roleId;

    /**
     * 权限id
     */
    @TableField("ACCESS_ID")
    private String accessId;

    /**
     * 权限类型（0 可访问/只读；1已授权/可编辑 ）
     */
    @TableField("ACCESS_TYPE")
    private Integer accessType;


}
