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
@TableName("sys_group")
public class SysGroup extends CommonEntity {


    /**
     * 组名称（部门、公司等组织）
     */
    @TableField("NAME")
    private String name;

    /**
     * 组编号（用户可用的唯一标识）
     */
    @TableField("CODE")
    private String code;

    /**
     * 父级部门id
     */
    @TableField("PARENT_ID")
    private String parentId;

    /**
     * 排序权重
     */
    @TableField("SORT")
    private Integer sort;

    /**
     * 其他配置（以json字符串存储，例如路由地址、图标名等）
     */
    @TableField("JSON_CONFIG")
    private String jsonConfig;

    /**
     * 备注说明（做多255个字符）
     */
    @TableField("REMARKS")
    private String remarks;


}
