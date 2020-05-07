package com.breaker.ssi;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.breaker.ssi.business.book.entity.Book;
import com.breaker.ssi.sys.entity.SysUser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.lang.reflect.Field;

@SpringBootTest
class SpringServerIntegrationApplicationTests {

    @Test
    void contextLoads() {
       String username = new BCryptPasswordEncoder().encode("admin");
       String password = new BCryptPasswordEncoder().encode("123456");
    }

}
