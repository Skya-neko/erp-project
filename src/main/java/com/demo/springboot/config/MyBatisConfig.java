package com.demo.springboot.config;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

@Configuration
public class MyBatisConfig {

    // 数据源
    @Resource
    private DataSource dataSource;

    // 资源文件(application.yml文件中mybatis下级资源)读取器
    @Resource
    private MybatisProperties properties;

    /**
     * 设置SqlSessionFactory, 并设置手动设置VFS为SpringBootVFS
     */
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean sqlSessionFactory() {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(this.dataSource);
        bean.setVfs(SpringBootVFS.class); // 关键，将vfs设置为SpringBootVFS
        bean.setTypeAliasesPackage(this.properties.getTypeAliasesPackage()); // 既然手动了，别忘了把其他的配置一起加入
        bean.setMapperLocations(this.properties.resolveMapperLocations());
        return bean;
    }

}
