package com.kuding.microservice.events;

import org.springframework.context.ApplicationListener;

import com.kuding.microservice.control.ServiceCheckControl;
import com.kuding.microservice.interfaces.ServiceCheckNoticeRepository;

public class ServiceExistedListener implements ApplicationListener<ServiceExistEvent> {

	private final ServiceCheckNoticeRepository serviceCheckNoticeRepository;

	private final ServiceCheckControl serviceCheckControl;

	public ServiceExistedListener(ServiceCheckNoticeRepository serviceCheckNoticeRepository,
			ServiceCheckControl serviceCheckControl) {
		this.serviceCheckNoticeRepository = serviceCheckNoticeRepository;
		this.serviceCheckControl = serviceCheckControl;
	}

	@Override
	public void onApplicationEvent(ServiceExistEvent event) {
		serviceCheckNoticeRepository.add(event.getLackServices(), event.getAdditionalServices());
		if (event.getAdditionalServices().size() > 0) {
			serviceCheckControl.addAll(event.getAdditionalServices());
		}
	}

}
