package com.demo.springboot.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.demo.springboot.dao") // 指定Mapper所在的package
public class PostgresSQLConfig {

}
