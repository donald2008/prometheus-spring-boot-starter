package com.kuding.microservice.config;

import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import com.kuding.microservice.properties.ServiceMonitorProperties;

@Configuration
@EnableConfigurationProperties({ ServiceMonitorProperties.class })
public class ServiceMonitorConfig {

	private static final String SCHEDULE_BEAN_NAME = "promethuesMicroServiceScheduler";

	@Bean(name = SCHEDULE_BEAN_NAME)
	@ConditionalOnMissingBean(name = SCHEDULE_BEAN_NAME)
	public ThreadPoolTaskScheduler threadPoolTaskScheduler(ServiceMonitorProperties serviceMonitorProperties) {
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setPoolSize(serviceMonitorProperties.getMonitorServices().size() + 10);
		scheduler.setThreadNamePrefix("prometheus-ms-");
		scheduler.setErrorHandler(x -> x.printStackTrace());
		scheduler.setRejectedExecutionHandler(new CallerRunsPolicy());
		scheduler.setWaitForTasksToCompleteOnShutdown(true);
		scheduler.setAwaitTerminationSeconds(1);
		return scheduler;
	}
}
