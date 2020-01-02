package com.breaker.ssi.utils.entity;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class DataEntity<T>  implements Serializable {
    protected static final long serialVersionUID = 1L;

    /**
     * 创建人
     */
    @TableField(value="CREATE_BY", fill = FieldFill.INSERT)
    protected String createBy;

    /**
     * 创建日期
     */
    @TableField(value="CREATE_DATE", fill = FieldFill.INSERT)
    @DateTimeFormat(pattern="yyyy/MM/dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy/MM/dd HH:mm")
    protected LocalDateTime createDate;

    /**
     * 修改人
     */
//    @TableField("UPDATE_BY")
    @TableField(value="UPDATE_BY", fill = FieldFill.INSERT_UPDATE)
    protected String updateBy;

    /**
     * 修改日期
     */
    @TableField(value="UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
    @DateTimeFormat(pattern="yyyy/MM/dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy/MM/dd HH:mm")
    protected LocalDateTime updateDate;

    /**
     * 删除标记（0：正常；1：删除；2：审核）
     */
    @TableField(value="DEL_FLAG", fill = FieldFill.INSERT)
    protected String delFlag;

    /**
     * 启用标记（0：停用；1：启用）
     */
    @TableField(value="FLAG", fill = FieldFill.INSERT)
    protected String flag;

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
        this.setFlag(FlagStatus.START.getStatus());
    }

    /**
     * 设置停用
     */
    public void setStop(){
        this.setFlag(FlagStatus.STOP.getStatus());
    }

    /**
     * 设置非删除&启用
     */
    public void setNormal(){
        this.setFlag(FlagStatus.START.getStatus());
        this.setDelFlag(DelStatus.NORMAL.getStatus());
    }


}
