package com.sx.oesb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
@MapperScan("com.sx.oesb.mapper")
@EnableOpenApi
@EnableCaching
public class OesbApplication {

    public static void main(String[] args) {
        SpringApplication.run(OesbApplication.class, args);
    }
}
