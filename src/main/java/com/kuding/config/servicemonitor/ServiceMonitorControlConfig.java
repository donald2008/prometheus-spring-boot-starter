package com.kuding.config.servicemonitor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;

import com.kuding.config.annos.ConditionalOnServiceMonitor;
import com.kuding.message.INoticeSendComponent;
import com.kuding.microservice.control.ServiceCheckControl;
import com.kuding.microservice.control.ServiceExistControl;
import com.kuding.microservice.control.ServiceNoticeControl;
import com.kuding.microservice.interfaces.HealthCheckHandler;
import com.kuding.microservice.interfaces.ReportedFilterHandler;
import com.kuding.microservice.interfaces.ServiceCheckNoticeRepository;
import com.kuding.pojos.servicemonitor.ServiceCheckNotice;
import com.kuding.properties.PromethreusNoticeProperties;
import com.kuding.properties.servicemonitor.ServiceMonitorProperties;

@Configuration
@ConditionalOnServiceMonitor
public class ServiceMonitorControlConfig {

	@Autowired
	private ServiceMonitorProperties serviceMonitorProperties;

	@Autowired
	private TaskScheduler promethuesMicroServiceScheduler;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Autowired
	private DiscoveryClient discoveryClient;

	@Bean
	public ServiceCheckControl serviceCheckControl(HealthCheckHandler healthCheckHandler) {
		ServiceCheckControl checkControl = new ServiceCheckControl(promethuesMicroServiceScheduler,
				serviceMonitorProperties, discoveryClient, applicationEventPublisher, healthCheckHandler);
		return checkControl;
	}

	@Bean
	public ServiceNoticeControl serviceNoticeControl(PromethreusNoticeProperties promethreusNoticeProperties,
			ServiceCheckNoticeRepository serviceCheckNoticeRepository,
			List<INoticeSendComponent<ServiceCheckNotice>> noticeSendComponents,
			ReportedFilterHandler reportedFilterHandler) {
		ServiceNoticeControl serviceNoticeControl = new ServiceNoticeControl(serviceMonitorProperties,
				promethreusNoticeProperties, promethuesMicroServiceScheduler, serviceCheckNoticeRepository,
				noticeSendComponents, reportedFilterHandler);
		return serviceNoticeControl;
	}

	@Bean
	public ServiceExistControl serviceExistControl() {
		ServiceExistControl serviceExistControl = new ServiceExistControl(promethuesMicroServiceScheduler,
				discoveryClient, applicationEventPublisher, serviceMonitorProperties);
		return serviceExistControl;
	}
}
