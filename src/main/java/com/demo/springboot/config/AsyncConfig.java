package com.demo.springboot.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
public class AsyncConfig {

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5); // 核心執行緒數量
        executor.setMaxPoolSize(10); // 最大執行緒數量
        executor.setQueueCapacity(25); // 隊列容量
        executor.setThreadNamePrefix("VioletAsync-");
        executor.initialize();
        return executor;
    }

    @Bean(name = "taskExecutor1")
    public Executor taskExecutor1() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5); // 核心執行緒數量
        executor.setMaxPoolSize(10); // 最大執行緒數量
        executor.setQueueCapacity(25); // 隊列容量
        executor.setThreadNamePrefix("VioletAsync111-");
        executor.initialize();
        return executor;
    }

}
