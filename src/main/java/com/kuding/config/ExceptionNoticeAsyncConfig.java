package com.kuding.config;

import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.kuding.exceptionhandle.ExceptionHandler;
import com.kuding.exceptionhandle.decorated.AsyncExceptionNoticeHandler;
import com.kuding.exceptionhandle.interfaces.ExceptionNoticeHandlerDecoration;
import com.kuding.properties.ExceptionNoticeAsyncProperties;

@Configuration
@ConditionalOnProperty(value = "exceptionnotice.enable-async-notice", havingValue = "true")
@EnableConfigurationProperties({ ExceptionNoticeAsyncProperties.class })
public class ExceptionNoticeAsyncConfig {

	@Autowired
	private ExceptionNoticeAsyncProperties noticeAsyncProperties;

	@Bean("exceptionnoitceExecutor")
	public ThreadPoolTaskExecutor exceptionnoitceExecutor() {
		ThreadPoolTaskExecutor poolTaskExecutor = new ThreadPoolTaskExecutor();
		poolTaskExecutor.setMaxPoolSize(noticeAsyncProperties.getMaxPoolSize());
		poolTaskExecutor.setCorePoolSize(noticeAsyncProperties.getCorePoolSize());
		poolTaskExecutor.setQueueCapacity(noticeAsyncProperties.getQueueCapacity());
		poolTaskExecutor.setThreadNamePrefix(noticeAsyncProperties.getThreadNamePrefix());
		poolTaskExecutor.setDaemon(noticeAsyncProperties.isDaemon());
		poolTaskExecutor.setRejectedExecutionHandler(new CallerRunsPolicy());
		poolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
		return poolTaskExecutor;
	}

	@Bean
	public ExceptionNoticeHandlerDecoration exceptionNoticeHandlerDecoration(ExceptionHandler exceptionHandler) {
		ExceptionNoticeHandlerDecoration decoration = new AsyncExceptionNoticeHandler(exceptionHandler,
				exceptionnoitceExecutor());
		return decoration;
	}
}
