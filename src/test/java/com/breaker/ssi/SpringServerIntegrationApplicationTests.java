package com.breaker.ssi;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.breaker.ssi.business.book.entity.Book;
import com.breaker.ssi.sys.entity.SysUser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;

@SpringBootTest
class SpringServerIntegrationApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("Hello, springboot!");
        try {
            SysUser sysUser = new SysUser();
            sysUser.setUsername("asdf");
            Class<?> clazz = Class.forName(sysUser.getClass().getName());
            TableName table = clazz.getAnnotation(TableName.class);
            String tableName = table.value();
            System.out.println("Book 类对应的表名" + tableName);
            // 获得字段注解
            Field fields[] = sysUser.getClass().getDeclaredFields();
            for (Field field : fields) {
                // 获取普通属性的@Column注解
                TableField tableField = field.getAnnotation(TableField.class);
                boolean accessFlag = field.isAccessible();
                field.setAccessible(true);
                if(field.get(sysUser) != null)
                    System.out.println(field.get(sysUser).toString());
            }} catch (ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
