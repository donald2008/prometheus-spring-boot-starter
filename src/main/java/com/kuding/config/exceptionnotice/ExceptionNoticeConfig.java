package com.kuding.config.exceptionnotice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kuding.config.annos.ConditionalOnExceptionNotice;
import com.kuding.exceptionhandle.ExceptionHandler;
import com.kuding.properties.PromethreusNoticeProperties;
import com.kuding.properties.exception.ExceptionNoticeProperties;

@Configuration
@ConditionalOnExceptionNotice
@EnableConfigurationProperties({ ExceptionNoticeProperties.class })
public class ExceptionNoticeConfig {

	private final Log logger = LogFactory.getLog(ExceptionNoticeConfig.class);

	@Bean
	public ExceptionHandler exceptionHandler(PromethreusNoticeProperties noticeProperties,
			ExceptionNoticeProperties exceptionNoticeProperties, ApplicationEventPublisher applicationEventPublisher) {
		logger.debug("创建异常处理器");
		ExceptionHandler exceptionHandler = new ExceptionHandler(noticeProperties, exceptionNoticeProperties,
				applicationEventPublisher);
		return exceptionHandler;
	}
}
