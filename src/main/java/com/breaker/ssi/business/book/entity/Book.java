package com.breaker.ssi.business.book.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.breaker.ssi.utils.entity.IdEntity;
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
 * @since 2020-01-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("book")
public class Book extends IdEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 书名称
     */
    @TableField("NAME")
    private String name;

    /**
     * 书价格
     */
    @TableField("PRICE")
    private Float price;

}
