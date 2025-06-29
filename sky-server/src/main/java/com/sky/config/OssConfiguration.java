package com.sky.config;

import com.sky.properties.AliOssProperties;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 配置类 用于创建AilOssUtil对象
 * 但其实这里可以直接在alioss类里面用@value注入
 */
@Configuration
@Slf4j
public class OssConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties) {
        log.info("创建阿里云文件上传工具类对象:{}", aliOssProperties);
        return new AliOssUtil(aliOssProperties.getEndpoint()
                ,aliOssProperties.getAccessKeyId()
                ,aliOssProperties.getAccessKeySecret()
                ,aliOssProperties.getBucketName());

    }
}
