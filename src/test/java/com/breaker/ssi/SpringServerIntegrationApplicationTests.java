package com.breaker.ssi;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.breaker.ssi.business.book.entity.Book;
import com.breaker.ssi.sys.entity.SysUser;
import com.breaker.ssi.sys.mapper.SysUserRoleMapper;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class SpringServerIntegrationApplicationTests {

    private String[] superEntityColumns;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Test
    void testUser() {
        List<String> accesses = sysUserRoleMapper.selectRolesByUser("359a59f10d7752259c51a6e44866fb81");
        String authorities = StringUtils.join(accesses, ",");
        System.out.println(accesses);
    }

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
