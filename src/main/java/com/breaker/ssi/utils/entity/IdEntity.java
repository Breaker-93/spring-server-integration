package com.breaker.ssi.utils.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public abstract class IdEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "BUSINESS_ID", type = IdType.ASSIGN_UUID)
    private String businessId;

}