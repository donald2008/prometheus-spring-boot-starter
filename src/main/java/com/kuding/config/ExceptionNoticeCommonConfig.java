package com.kuding.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kuding.aop.ExceptionNoticeAop;
import com.kuding.exceptionhandle.interfaces.ExceptionNoticeHandlerDecoration;

@Configuration
@ConditionalOnProperty(name = "exceptionnotice.listen-type", havingValue = "common", matchIfMissing = true)
@ConditionalOnBean({ ExceptionNoticeHandlerDecoration.class })
public class ExceptionNoticeCommonConfig {

	private final Log logger = LogFactory.getLog(getClass());

	public ExceptionNoticeCommonConfig() {
		logger.debug("------------加载ExceptionNoticeCommonConfig");
	}

	@Bean
	@ConditionalOnMissingBean
	public ExceptionNoticeAop exceptionNoticeAop(ExceptionNoticeHandlerDecoration exceptionHandler) {
		ExceptionNoticeAop aop = new ExceptionNoticeAop(exceptionHandler);
		return aop;
	}
}
