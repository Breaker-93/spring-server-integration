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
@TableName("sys_access")
public class SysAccess extends CommonEntity {


    /**
     * 权限名称(最大50个字符)
     */
    @TableField("NAME")
    private String name;

    /**
     * 权限编码（最大50个字符）
     */
    @TableField("CODE")
    private String code;

    /**
     * 父级权限id
     */
    @TableField("PARENT_ID")
    private String parentId;

    /**
     * 排序权重
     */
    @TableField("SORT")
    private Integer sort;

    /**
     * 备注说明（做多255个字符）
     */
    @TableField("REMARKS")
    private String remarks;

    /**
     * 其他配置（以json字符串存储，例如路由地址、图标名等）
     */
    @TableField("JSON_CONFIG")
    private String jsonConfig;


}
