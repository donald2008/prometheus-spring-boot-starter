package com.kuding.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import com.kuding.config.conditions.PrometheusEnabledCondition;
import com.kuding.properties.PromethreusNoticeProperties;

@Configuration
@Conditional(PrometheusEnabledCondition.class)
@EnableConfigurationProperties(PromethreusNoticeProperties.class)
public class PromethuesConfig {

}
