package org.example.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ExecutorSpringConfig {

    /**
     * 自定义线程池
     * @param customizeExecutorProperties
     * @return
     */
//    @Lazy
    @Bean("customizeExecutor")
    public ThreadPoolExecutor customizeExecutor(CustomizeExecutorProperties customizeExecutorProperties) {
        final CustomizeExecutorProperties.DefaultExecutorProperties properties = customizeExecutorProperties.getDefaultExecutor();
        ThreadFactory springThreadFactory = new CustomizableThreadFactory("自定义-pool-");
        return new ThreadPoolExecutor(
                properties.getCorePoolSize(),
                properties.getMaximumPoolSize(),
                properties.getKeepAliveTime(),
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(properties.getQueueSize()),
                springThreadFactory,
                new CustomizeRejectedExecutionHandler.AbortPolicy());
    }

}
