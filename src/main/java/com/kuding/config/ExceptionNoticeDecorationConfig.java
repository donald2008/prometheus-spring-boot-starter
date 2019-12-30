package com.kuding.config;

import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.kuding.exceptionhandle.ExceptionHandler;
import com.kuding.exceptionhandle.decorated.AsyncExceptionNoticeHandler;
import com.kuding.exceptionhandle.decorated.DefaultExceptionNoticeHandler;
import com.kuding.exceptionhandle.interfaces.ExceptionNoticeHandlerDecoration;
import com.kuding.properties.ExceptionNoticeAsyncProperties;

@Configuration
@ConditionalOnProperty(name = "exceptionnotice.open-notice", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties({ ExceptionNoticeAsyncProperties.class })
public class ExceptionNoticeDecorationConfig {

	@Autowired
	private ExceptionNoticeAsyncProperties noticeAsyncProperties;

	private final Log logger = LogFactory.getLog(getClass());

	public ExceptionNoticeDecorationConfig() {
		logger.debug("------------加载ExceptionNoticeDecorationConfig");
	}

	@Bean
	@ConditionalOnProperty(value = "exceptionnotice.enable-async-notice", havingValue = "true")
	public ExceptionNoticeHandlerDecoration exceptionNoticeHandlerDecoration(ExceptionHandler exceptionHandler) {
		logger.debug("创建异步通知组件");
		ThreadPoolTaskExecutor poolTaskExecutor = new ThreadPoolTaskExecutor();
		poolTaskExecutor.setMaxPoolSize(noticeAsyncProperties.getMaxPoolSize());
		poolTaskExecutor.setCorePoolSize(noticeAsyncProperties.getCorePoolSize());
		poolTaskExecutor.setQueueCapacity(noticeAsyncProperties.getQueueCapacity());
		poolTaskExecutor.setThreadNamePrefix(noticeAsyncProperties.getThreadNamePrefix());
		poolTaskExecutor.setDaemon(noticeAsyncProperties.isDaemon());
		poolTaskExecutor.setRejectedExecutionHandler(new CallerRunsPolicy());
		poolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
		poolTaskExecutor.initialize();
		ExceptionNoticeHandlerDecoration decoration = new AsyncExceptionNoticeHandler(exceptionHandler,
				poolTaskExecutor);
		return decoration;
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(value = "exceptionnotice.enable-async-notice", matchIfMissing = true, havingValue = "false")
	public ExceptionNoticeHandlerDecoration defaultExceptionNoticeHandlerDecoration(ExceptionHandler exceptionHandler) {
		logger.debug("创建默认通知组件");
		ExceptionNoticeHandlerDecoration decoration = new DefaultExceptionNoticeHandler(exceptionHandler);
		return decoration;
	}

}
