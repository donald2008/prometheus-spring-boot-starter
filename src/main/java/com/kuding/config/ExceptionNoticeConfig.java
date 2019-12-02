package com.kuding.config;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import com.kuding.aop.ExceptionNoticeAop;
import com.kuding.exceptionhandle.ExceptionHandler;
import com.kuding.httpclient.DefaultDingdingHttpClient;
import com.kuding.httpclient.DingdingHttpClient;
import com.kuding.message.DingDingNoticeSendComponent;
import com.kuding.message.INoticeSendComponent;
import com.kuding.properties.DingDingExceptionNoticeProperty;
import com.kuding.properties.ExceptionNoticeFrequencyStrategy;
import com.kuding.properties.ExceptionNoticeProperty;

@Configuration
@EnableConfigurationProperties({ ExceptionNoticeProperty.class, ExceptionNoticeFrequencyStrategy.class })
@ConditionalOnMissingBean({ ExceptionHandler.class })
@ConditionalOnProperty(name = "exceptionnotice.open-notice", havingValue = "true", matchIfMissing = true)
@EnableScheduling
public class ExceptionNoticeConfig {

	@Autowired
	private ExceptionNoticeProperty exceptionNoticeProperty;

	@Autowired
	private ExceptionNoticeFrequencyStrategy exceptionNoticeFrequencyStrategy;

	@Autowired(required = false)
	private INoticeSendComponent noticeSendComponent;

	@Autowired
	private RestTemplateBuilder restTemplateBuilder;

	@Bean
	@ConditionalOnProperty(name = "exceptionnotice.listen-type", havingValue = "common", matchIfMissing = true)
	@ConditionalOnMissingBean(ExceptionNoticeAop.class)
	public ExceptionNoticeAop exceptionNoticeAop(ExceptionHandler exceptionHandler) {
		ExceptionNoticeAop aop = new ExceptionNoticeAop(exceptionHandler);
		return aop;
	}

	@Bean
	@ConditionalOnMissingBean({ ExceptionHandler.class })
	public ExceptionHandler exceptionHandler(DingdingHttpClient httpClient) {
		Map<String, DingDingExceptionNoticeProperty> dingding = exceptionNoticeProperty.getDingding();
		List<INoticeSendComponent> list = new LinkedList<INoticeSendComponent>();
		if (noticeSendComponent != null)
			list.add(noticeSendComponent);
		if (dingding != null && dingding.size() > 0) {
			DingDingNoticeSendComponent component = new DingDingNoticeSendComponent(httpClient, exceptionNoticeProperty,
					dingding);
			list.add(component);
		}

		ExceptionHandler exceptionHandler = new ExceptionHandler(exceptionNoticeProperty, list,
				exceptionNoticeFrequencyStrategy);
		return exceptionHandler;
	}

//	@Bean
//	public SimpleHttpClient simpleHttpClient() {
//		SimpleHttpClient httpClient = new SimpleHttpClient(gson);
//		return httpClient;
//	}

	@Bean
	@ConditionalOnMissingBean({ DingdingHttpClient.class })
	public DingdingHttpClient dingdingHttpClient() {
		RestTemplate restTemplate = restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(20)).build();
		DingdingHttpClient dingdingHttpClient = new DefaultDingdingHttpClient(restTemplate);
		return dingdingHttpClient;

	}

}
