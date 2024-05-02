package com.atguigu.cloud.config;

import feign.Logger;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kexiaobin
 */
@Configuration
public class FeignConfig {

    /**
     * 重试机制
     */
    @Bean
    public Retryer myRetryer() {
        // 最大请求数为3（1+2), 初始间隔为100ms，重试间隔为1000ms
//        return new Retryer.Default(100, 1,3);
        return Retryer.NEVER_RETRY;
    }

    @Bean
    public Logger.Level feginLoggerLevel() {
        return Logger.Level.FULL;
    }
}
