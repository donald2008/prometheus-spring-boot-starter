package com.kuding.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;
import com.kuding.httpclient.DefaultDingdingHttpClient;
import com.kuding.httpclient.DingdingHttpClient;
import com.kuding.properties.DingDingNoticeProperty;

@Configuration
@ConditionalOnProperty(value = "prometheus.dingding.enabled", havingValue = "true")
@EnableConfigurationProperties({ DingDingNoticeProperty.class })
public class PromethuesDingDingNoticeConfig {

	@Bean
	@ConditionalOnMissingBean
	public DingdingHttpClient dingdingHttpClient(DingDingNoticeProperty dingDingNoticeProperty, Gson gson) {
		DingdingHttpClient dingdingHttpClient = new DefaultDingdingHttpClient(gson, dingDingNoticeProperty);
		return dingdingHttpClient;
	}
}
