package com.kuding.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;
import com.kuding.exceptionhandle.ExceptionHandler;
import com.kuding.httpclient.SimpleHttpClient;
import com.kuding.message.DingDingNoticeSendComponent;
import com.kuding.message.INoticeSendComponent;
import com.kuding.properties.DingDingExceptionNoticeProperty;
import com.kuding.properties.ExceptionNoticeProperty;

@Configuration
@ConditionalOnProperty(value = "exceptionnotice.notice-type", havingValue = "dingding")
@AutoConfigureAfter({ ExceptionNoticeConfig.class })
@ConditionalOnBean(ExceptionHandler.class)
@ConditionalOnMissingBean({ INoticeSendComponent.class })
@EnableConfigurationProperties({ DingDingExceptionNoticeProperty.class })
public class DingdingExceptionNoticeConfig {

	@Autowired
	private Gson gson;

	@Bean
	public SimpleHttpClient simpleHttpClient() {
		SimpleHttpClient httpClient = new SimpleHttpClient(gson);
		return httpClient;
	}

	@Bean
	public DingDingNoticeSendComponent dingDingNoticeSendComponent(ExceptionHandler exceptionHandler,
			ExceptionNoticeProperty exceptionNoticeProperty,
			DingDingExceptionNoticeProperty dingDingExceptionNoticeProperty) {
		DingDingNoticeSendComponent dingDingNoticeSendComponent = new DingDingNoticeSendComponent(simpleHttpClient(),
				exceptionNoticeProperty, dingDingExceptionNoticeProperty);
		exceptionHandler.setSendComponent(dingDingNoticeSendComponent);
		return dingDingNoticeSendComponent;
	}
}
