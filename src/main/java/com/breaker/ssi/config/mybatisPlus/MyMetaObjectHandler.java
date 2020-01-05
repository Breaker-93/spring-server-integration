package com.breaker.ssi.config.mybatisPlus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @description: This is a handler class for meta object.
 * @author: Breaker93
 * @createTime: 2019/9/21 11:08
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        this.setFieldValByName("businessId",  new UUID(random.nextLong(), random.nextLong()).toString().replace("-", ""), metaObject);
        this.setFieldValByName("createBy", userId, metaObject);
        this.setFieldValByName("createDate", LocalDateTime.now(), metaObject);
        this.setFieldValByName("updateBy", userId, metaObject);
        this.setFieldValByName("updateDate", LocalDateTime.now(), metaObject);
        this.setFieldValByName("delFlag", "0", metaObject);
        this.setFieldValByName("flag", "1", metaObject);

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        this.setFieldValByName("updateBy", userId, metaObject);
        this.setFieldValByName("updateDate", LocalDateTime.now(), metaObject);
    }
}
