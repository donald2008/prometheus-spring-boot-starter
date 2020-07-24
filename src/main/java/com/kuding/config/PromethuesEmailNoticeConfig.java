package com.kuding.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.kuding.properties.EmailNoticeProperty;

@Configuration
@ConditionalOnProperty(value = "prometheus.email.enabled", havingValue = "true")
@EnableConfigurationProperties({ EmailNoticeProperty.class })
public class PromethuesEmailNoticeConfig {

}
