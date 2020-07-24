package com.kuding.config.servicemonitor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kuding.config.annos.ConditionalOnServiceMonitor;
import com.kuding.httpclient.DingdingHttpClient;
import com.kuding.message.DingDingNoticeSendComponent;
import com.kuding.message.INoticeSendComponent;
import com.kuding.pojos.servicemonitor.ServiceCheckNotice;
import com.kuding.properties.DingDingNoticeProperty;
import com.kuding.properties.enums.DingdingTextType;
import com.kuding.text.ServiceMonitorResolver;
import com.kuding.text.markdown.ServiceMonitorMarkdownResolver;

@Configuration
@ConditionalOnServiceMonitor
public class ServiceMonitorDingdingSendingConfig {

	private final Log logger = LogFactory.getLog(ServiceMonitorDingdingSendingConfig.class);

	@Bean
	@ConditionalOnMissingBean(parameterizedContainer = INoticeSendComponent.class)
	public INoticeSendComponent<ServiceCheckNotice> addSendComponent(DingdingHttpClient dingdingHttpClient,
			ServiceMonitorResolver exceptionNoticeResolver, DingDingNoticeProperty dingDingNoticeProperty) {
		logger.debug("注册钉钉通知");
		INoticeSendComponent<ServiceCheckNotice> component = new DingDingNoticeSendComponent<ServiceCheckNotice>(
				dingdingHttpClient, exceptionNoticeResolver, dingDingNoticeProperty);
		return component;
	}

	@Bean
	@ConditionalOnMissingBean
	public ServiceMonitorResolver serviceMonitorResolver(DingDingNoticeProperty dingDingNoticeProperty) {
		if (dingDingNoticeProperty.getDingdingTextType() == DingdingTextType.MARKDOWN)
			return new ServiceMonitorMarkdownResolver();
		return x -> x.generateText();
	}

}
