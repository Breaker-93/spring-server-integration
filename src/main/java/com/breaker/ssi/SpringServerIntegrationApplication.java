package com.breaker.ssi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.breaker.ssi.**.mapper")
public class SpringServerIntegrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringServerIntegrationApplication.class, args);
    }

}
