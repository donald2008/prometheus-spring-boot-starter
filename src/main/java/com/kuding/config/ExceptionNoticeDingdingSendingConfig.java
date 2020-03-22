package com.kuding.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.kuding.httpclient.DefaultDingdingHttpClient;
import com.kuding.httpclient.DingdingHttpClient;
import com.kuding.markdown.DefaultMarkdownMessageResolver;
import com.kuding.message.DingDingNoticeSendComponent;
import com.kuding.message.INoticeSendComponent;
import com.kuding.properties.DingDingExceptionNoticeProperty;
import com.kuding.properties.enums.DingdingTextType;
import com.kuding.text.ExceptionNoticeTextResolver;

@Configuration
@ConditionalOnProperty(name = "exceptionnotice.open-notice", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties({ DingDingExceptionNoticeProperty.class })
public class ExceptionNoticeDingdingSendingConfig {

	@Autowired
	private DingDingExceptionNoticeProperty exceptionNoticeProperty;

	private final Log logger = LogFactory.getLog(ExceptionNoticeDingdingSendingConfig.class);

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(value = "exceptionnotice.notice-type", havingValue = "dingding", matchIfMissing = true)
	public INoticeSendComponent addSendComponent(DingdingHttpClient dingdingHttpClient,
			ExceptionNoticeTextResolver exceptionNoticeResolver) {
		logger.debug("注册钉钉通知");
		INoticeSendComponent component = new DingDingNoticeSendComponent(dingdingHttpClient, exceptionNoticeProperty,
				exceptionNoticeResolver);
		return component;
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(value = "exceptionnotice.notice-type", havingValue = "dingding", matchIfMissing = true)
	public ExceptionNoticeTextResolver ExceptionNoticeTextResolver() {
		if (exceptionNoticeProperty.getDingdingTextType() == DingdingTextType.MARKDOWN)
			return new DefaultMarkdownMessageResolver();
		return x -> x.createText();
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(value = "exceptionnotice.notice-type", havingValue = "dingding", matchIfMissing = true)
	public DingdingHttpClient dingdingHttpClient(RestTemplateBuilder restTemplateBuilder) {
		RestTemplate restTemplate = restTemplateBuilder.build();
		DefaultDingdingHttpClient defaultDingdingHttpClient = new DefaultDingdingHttpClient(restTemplate);
		return defaultDingdingHttpClient;
	}

}
