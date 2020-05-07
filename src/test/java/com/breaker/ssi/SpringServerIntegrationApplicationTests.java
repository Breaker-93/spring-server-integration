package com.breaker.ssi;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.breaker.ssi.business.book.entity.Book;
import com.breaker.ssi.sys.entity.SysUser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.lang.reflect.Field;
import java.util.Arrays;

@SpringBootTest
class SpringServerIntegrationApplicationTests {

    private String[] superEntityColumns;
    @Test
    void contextLoads() {
       String username = new BCryptPasswordEncoder().encode("admin");
       String password = new BCryptPasswordEncoder().encode("123456");
       superEntityColumns = new String[] { "BUSINESS_ID", "CREATE_BY", "CREATE_TIME", "UPDATE_BY", "UPDATE_TIME", "DEL_FLAG", "USE_FLAG"};
       System.out.println(includeSuperEntityColumns("UPDATE_Bysad"));

    }
    public boolean includeSuperEntityColumns(String fieldName) {
        return null != this.superEntityColumns ? Arrays.stream(this.superEntityColumns).anyMatch((e) -> {
            return e.equalsIgnoreCase(fieldName);
        }) : false;
    }

}
