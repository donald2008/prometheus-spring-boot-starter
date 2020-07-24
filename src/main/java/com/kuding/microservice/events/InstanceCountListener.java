package com.kuding.microservice.events;

import org.springframework.context.ApplicationListener;

import com.kuding.microservice.interfaces.ServiceCheckNoticeRepository;
import com.kuding.pojos.servicemonitor.ServiceInstanceLackProblem;

public class InstanceCountListener implements ApplicationListener<InstanceCountEvent> {

	private final ServiceCheckNoticeRepository serviceCheckNoticeRepository;

	/**
	 * @param serviceCheckNoticeRepository
	 */
	public InstanceCountListener(ServiceCheckNoticeRepository serviceCheckNoticeRepository) {
		this.serviceCheckNoticeRepository = serviceCheckNoticeRepository;
	}

	@Override
	public void onApplicationEvent(InstanceCountEvent event) {
		serviceCheckNoticeRepository.add(new ServiceInstanceLackProblem(event));
	}

}
