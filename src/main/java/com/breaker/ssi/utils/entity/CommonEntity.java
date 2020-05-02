package com.breaker.ssi.utils.entity;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class CommonEntity extends IdEntity implements Serializable {

    protected static final long serialVersionUID = 1L;

    /**
     * 创建人
     */
    @TableField(value="CREATE_BY", fill = FieldFill.INSERT)
    protected String createBy;

    /**
     * 创建日期
     */
    @TableField(value="CREATE_TIME", fill = FieldFill.INSERT)
    protected LocalDateTime createTime;

    /**
     * 修改人
     */
    @TableField(value="UPDATE_BY", fill = FieldFill.INSERT_UPDATE)
    protected String updateBy;

    /**
     * 修改日期
     */
    @TableField(value="UPDATE_TIME", fill = FieldFill.INSERT_UPDATE)
    protected LocalDateTime updateTime;

    /**
     * 删除标记（0：正常；1：删除；2：审核）
     */
    @TableField(value="DEL_FLAG", fill = FieldFill.INSERT)
    protected String delFlag;

    /**
     * 启用标记（0：停用；1：启用）
     */
    @TableField(value="USE_FLAG", fill = FieldFill.INSERT)
    protected String useFlag;

    /**
     * 设置删除
     */
    public void setDeleted(){
        this.setDelFlag(DelStatus.DELETED.getStatus());
    }

    /**
     * 设置非删除
     */
    public void setNotDeleted(){
        this.setDelFlag(DelStatus.NORMAL.getStatus());
    }


    /**
     * 设置启用
     */
    public void setStart(){
        this.setUseFlag(UseStatus.START.getStatus());
    }

    /**
     * 设置停用
     */
    public void setStop(){
        this.setUseFlag(UseStatus.STOP.getStatus());
    }

    /**
     * 设置非删除&启用
     */
    public void setNormal(){
        this.setUseFlag(UseStatus.START.getStatus());
        this.setDelFlag(DelStatus.NORMAL.getStatus());
    }

}