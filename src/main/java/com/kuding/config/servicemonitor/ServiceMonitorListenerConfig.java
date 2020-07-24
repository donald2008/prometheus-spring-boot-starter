package com.kuding.config.servicemonitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kuding.config.annos.ConditionalOnServiceMonitor;
import com.kuding.microservice.control.ServiceCheckControl;
import com.kuding.microservice.events.InstanceCountListener;
import com.kuding.microservice.events.InstanceHealthListener;
import com.kuding.microservice.events.ServiceExistedListener;
import com.kuding.microservice.interfaces.ServiceCheckNoticeRepository;

@Configuration
@ConditionalOnServiceMonitor
public class ServiceMonitorListenerConfig {

	@Autowired
	private ServiceCheckNoticeRepository serviceCheckNoticeRepository;

	@Bean
	public InstanceCountListener instanceCountListener() {
		InstanceCountListener listener = new InstanceCountListener(serviceCheckNoticeRepository);
		return listener;
	}

	@Bean
	public InstanceHealthListener instanceHealthListener() {
		InstanceHealthListener listener = new InstanceHealthListener(serviceCheckNoticeRepository);
		return listener;
	}

	@Bean
	public ServiceExistedListener serviceExistedListener(ServiceCheckControl serviceCheckControl) {
		ServiceExistedListener existedListener = new ServiceExistedListener(serviceCheckNoticeRepository,
				serviceCheckControl);
		return existedListener;
	}
}
