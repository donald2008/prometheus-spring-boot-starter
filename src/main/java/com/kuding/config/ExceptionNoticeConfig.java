package com.kuding.config;

import java.time.Duration;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import com.kuding.exceptionhandle.ExceptionHandler;
import com.kuding.httpclient.DefaultDingdingHttpClient;
import com.kuding.httpclient.DingdingHttpClient;
import com.kuding.markdown.DefaultMarkdownHttpMessageResolver;
import com.kuding.markdown.DefaultMarkdownMessageResolver;
import com.kuding.properties.ExceptionNoticeFrequencyStrategy;
import com.kuding.properties.ExceptionNoticeProperty;
import com.kuding.properties.enums.DingdingTextType;
import com.kuding.properties.enums.ListenType;
import com.kuding.text.ExceptionNoticeResolver;
import com.kuding.text.ExceptionNoticeResolverFactory;

@Configuration
@EnableConfigurationProperties({ ExceptionNoticeProperty.class, ExceptionNoticeFrequencyStrategy.class })
@ConditionalOnProperty(name = "exceptionnotice.open-notice", havingValue = "true", matchIfMissing = true)
@EnableScheduling
public class ExceptionNoticeConfig {

	@Autowired
	private ExceptionNoticeProperty exceptionNoticeProperty;

	@Autowired
	private ExceptionNoticeFrequencyStrategy exceptionNoticeFrequencyStrategy;

	@Autowired
	private RestTemplateBuilder restTemplateBuilder;

	private final ExceptionSendConfigComposite exceptionSendConfigComposite = new ExceptionSendConfigComposite();

	private final Log logger = LogFactory.getLog(getClass());

	public ExceptionNoticeConfig() {
		logger.debug("------------加载ExceptionNoticeConfig");
	}

	@Autowired(required = false)
	public void setSendConfig(List<ExceptionSendComponentConfigure> configures) {
		logger.debug("发送组件数量：" + configures.size());
		exceptionSendConfigComposite.addAll(configures);
	}

	private void regist(ExceptionHandler exceptionHandler) {
		exceptionSendConfigComposite.regist(exceptionHandler);
	}

	@Bean
	@ConditionalOnMissingBean
	public ExceptionNoticeResolverFactory exceptionNoticeResolverFactory() {
		ExceptionNoticeResolverFactory exceptionNoticeResolverFactory = new ExceptionNoticeResolverFactory();
		if (exceptionNoticeProperty.getDingdingTextType() == DingdingTextType.MARKDOWN) {
			ExceptionNoticeResolver exceptionNoticeResolver = null;
			if (exceptionNoticeProperty.getListenType() == ListenType.COMMON)
				exceptionNoticeResolver = new DefaultMarkdownMessageResolver(exceptionNoticeProperty);
			if (exceptionNoticeProperty.getListenType() == ListenType.WEB_MVC)
				exceptionNoticeResolver = new DefaultMarkdownHttpMessageResolver(exceptionNoticeProperty);
			exceptionNoticeResolverFactory.addNoticeResolver("dingding", exceptionNoticeResolver);
		}
		return exceptionNoticeResolverFactory;
	}

	@Bean
	@ConditionalOnMissingBean({ ExceptionHandler.class })
	public ExceptionHandler exceptionHandler(DingdingHttpClient httpClient,
			ExceptionNoticeResolverFactory exceptionNoticeResolverFactory) {
		ExceptionHandler exceptionHandler = new ExceptionHandler(exceptionNoticeProperty,
				exceptionNoticeFrequencyStrategy);
		regist(exceptionHandler);
		return exceptionHandler;
	}

	@Bean
	@ConditionalOnMissingBean
	public DingdingHttpClient dingdingHttpClient() {
		RestTemplate restTemplate = restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(20)).build();
		DingdingHttpClient dingdingHttpClient = new DefaultDingdingHttpClient(restTemplate);
		return dingdingHttpClient;
	}
}
