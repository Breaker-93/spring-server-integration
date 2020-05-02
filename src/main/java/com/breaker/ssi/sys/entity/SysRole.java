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
 * @since 2020-05-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_role")
public class SysRole extends CommonEntity {


    /**
     * 角色名称
     */
    @TableField("NAME")
    private String name;

    /**
     * 角色编码
     */
    @TableField("CODE")
    private String code;

    /**
     * 备注说明（255个字符以内）
     */
    @TableField("REMARKS")
    private String remarks;


}
