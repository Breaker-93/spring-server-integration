package com.breaker.ssi.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.breaker.ssi.utils.entity.IdEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Breaker-93
 * @since 2020-05-02
 */
@Data
@Accessors(chain = true)
@TableName("sys_log")
public class SysLog extends IdEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 操作内容（50个字符以内）
     */
    @TableField("OPERATE_CONTENT")
    private String operateContent;

    /**
     * 操作结果（0失败，1成功）
     */
    @TableField("OPERATE_RESULT")
    private Integer operateResult;

    /**
     * 操作人的IP地址
     */
    @TableField("OPERATE_IP")
    private String operateIp;

    /**
     * 操作时间
     */
    @TableField("OPERATE_TIME")
    private LocalDateTime operateTime;

    /**
     * 操作人
     */
    @TableField("OPERATOR_ID")
    private String operatorId;

    /**
     * 失败原因（300个字符以内）
     */
    @TableField("FAIL_REASON")
    private String failReason;


}
