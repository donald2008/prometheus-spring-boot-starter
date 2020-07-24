package com.kuding.config.servicemonitor;

import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import com.kuding.config.annos.ConditionalOnServiceMonitor;
import com.kuding.microservice.components.DefaultReportFilterHandler;
import com.kuding.microservice.components.InMemeryServiceCheckNoticeRepository;
import com.kuding.microservice.interfaces.ReportedFilterHandler;
import com.kuding.microservice.interfaces.ServiceCheckNoticeRepository;
import com.kuding.properties.servicemonitor.ServiceMonitorProperties;

@Configuration
@ConditionalOnServiceMonitor
@EnableConfigurationProperties({ ServiceMonitorProperties.class })
public class ServiceMonitorConfig {

	private static final String SCHEDULE_BEAN_NAME = "promethuesMicroServiceScheduler";

	@Bean(name = SCHEDULE_BEAN_NAME)
	@ConditionalOnMissingBean(name = SCHEDULE_BEAN_NAME)
	@Qualifier
	public TaskScheduler promethuesMicroServiceScheduler(ServiceMonitorProperties serviceMonitorProperties) {
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setPoolSize(serviceMonitorProperties.getMonitorServices().size() + 10);
		scheduler.setThreadNamePrefix("prometheus-ms-");
		scheduler.setErrorHandler(x -> x.printStackTrace());
		scheduler.setRejectedExecutionHandler(new CallerRunsPolicy());
		scheduler.setWaitForTasksToCompleteOnShutdown(true);
		scheduler.setAwaitTerminationSeconds(1);
		return scheduler;
	}

	@Bean
	@ConditionalOnMissingBean
	public ServiceCheckNoticeRepository serviceCheckNoticeRepository(
			ServiceMonitorProperties serviceMonitorProperties) {
		Set<String> set = serviceMonitorProperties.getMonitorServices().keySet();
		ServiceCheckNoticeRepository repository = new InMemeryServiceCheckNoticeRepository(set);
		return repository;
	}

	@Bean
	@ConditionalOnMissingBean
	public ReportedFilterHandler reportedFilterHandler() {
		ReportedFilterHandler filterHandler = new DefaultReportFilterHandler();
		return filterHandler;
	}

}
