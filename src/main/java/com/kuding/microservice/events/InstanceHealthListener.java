package com.kuding.microservice.events;

import org.springframework.context.ApplicationListener;

import com.kuding.microservice.interfaces.ServiceCheckNoticeRepository;
import com.kuding.pojos.servicemonitor.ServiceHealthProblem;

public class InstanceHealthListener implements ApplicationListener<InstanceHealthEvent> {

	private final ServiceCheckNoticeRepository serviceCheckNoticeRepository;

	public InstanceHealthListener(ServiceCheckNoticeRepository serviceCheckNoticeRepository) {
		this.serviceCheckNoticeRepository = serviceCheckNoticeRepository;
	}

	@Override
	public void onApplicationEvent(InstanceHealthEvent event) {
		serviceCheckNoticeRepository.add(new ServiceHealthProblem(event));
	}

}
