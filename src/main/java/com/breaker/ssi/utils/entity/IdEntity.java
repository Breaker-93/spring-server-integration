package com.breaker.ssi.utils.entity;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public abstract class IdEntity<T> extends DataEntity<T>  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId("BUSINESS_ID")
    @TableField(fill = FieldFill.INSERT)
    private String businessId;

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }



    /**
     * 通用设置ID
     */
    public void setCommonBusinessId(){
        this.setBusinessId(UUID.randomUUID().toString().replaceAll("-", ""));
    }
}