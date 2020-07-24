package com.kuding.config.exceptionnotice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kuding.config.annos.ConditionalOnExceptionNotice;
import com.kuding.httpclient.DingdingHttpClient;
import com.kuding.message.DingDingNoticeSendComponent;
import com.kuding.message.INoticeSendComponent;
import com.kuding.pojos.ExceptionNotice;
import com.kuding.properties.DingDingNoticeProperty;
import com.kuding.properties.enums.DingdingTextType;
import com.kuding.text.ExceptionNoticeResolver;
import com.kuding.text.markdown.ExceptionNoticeMarkdownMessageResolver;

@Configuration
@ConditionalOnExceptionNotice
public class ExceptionNoticeDingdingSendingConfig {

	private final Log logger = LogFactory.getLog(ExceptionNoticeDingdingSendingConfig.class);

	@Bean
	@ConditionalOnMissingBean(parameterizedContainer = INoticeSendComponent.class)
	public INoticeSendComponent<ExceptionNotice> sendComponent(DingdingHttpClient dingdingHttpClient,
			ExceptionNoticeResolver exceptionNoticeResolver, DingDingNoticeProperty dingDingNoticeProperty) {
		logger.debug("注册钉钉通知");
		INoticeSendComponent<ExceptionNotice> component = new DingDingNoticeSendComponent<ExceptionNotice>(
				dingdingHttpClient, exceptionNoticeResolver, dingDingNoticeProperty);
		return component;
	}

	@Bean
	@ConditionalOnMissingBean
	public ExceptionNoticeResolver ExceptionNoticeTextResolver(DingDingNoticeProperty dingDingNoticeProperty) {
		if (dingDingNoticeProperty.getDingdingTextType() == DingdingTextType.MARKDOWN)
			return new ExceptionNoticeMarkdownMessageResolver();
		return x -> x.createText();
	}

}
